package com.storyverse.billing.application;

import com.storyverse.billing.domain.Payment;
import com.storyverse.billing.domain.event.PaymentCompletedEvent;
import com.storyverse.billing.ports.*;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.domain.exception.BusinessRuleViolationException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProcessPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayPort paymentGateway;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public ProcessPaymentUseCase(PaymentRepository paymentRepository, PaymentGatewayPort paymentGateway,
                                  EventPublisher eventPublisher, SecurityContext securityContext) {
        this.paymentRepository = paymentRepository;
        this.paymentGateway = paymentGateway;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(UUID subscriptionId, BigDecimal amount, String currency, String paymentMethod) {
        UUID userId = securityContext.requireUserId();
        Payment payment = Payment.create(UUID.randomUUID(), userId, subscriptionId, amount, currency, paymentMethod);

        PaymentGatewayResult result = paymentGateway.charge(userId.toString(), amount, currency, paymentMethod);
        if (result.success()) {
            payment.complete(result.transactionId());
            eventPublisher.publish(new PaymentCompletedEvent(payment.getId(), userId, amount, EventMetadata.createNew(userId.toString())));
        } else {
            payment.fail();
            throw new BusinessRuleViolationException("PAYMENT_FAILED", "Payment failed: " + result.errorMessage());
        }

        paymentRepository.save(payment);
        return payment.getId();
    }
}
