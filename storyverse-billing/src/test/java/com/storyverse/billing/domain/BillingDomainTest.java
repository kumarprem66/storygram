package com.storyverse.billing.domain;

import com.storyverse.billing.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BillingDomainTest {

    @Nested @DisplayName("Subscription")
    class SubscriptionTests {
        @Test @DisplayName("Should create with ACTIVE status")
        void shouldCreateActive() {
            Subscription s = Subscription.create(UUID.randomUUID(), UUID.randomUUID(), SubscriptionPlan.PREMIUM);
            assertEquals(SubscriptionStatus.ACTIVE, s.getStatus());
            assertEquals(SubscriptionPlan.PREMIUM, s.getPlan());
            assertTrue(s.isAutoRenew());
            assertTrue(s.isActive());
            assertNotNull(s.getStartDate());
            assertNotNull(s.getEndDate());
        }

        @Test @DisplayName("Should cancel subscription")
        void shouldCancel() {
            Subscription s = Subscription.create(UUID.randomUUID(), UUID.randomUUID(), SubscriptionPlan.PREMIUM);
            s.cancel();
            assertEquals(SubscriptionStatus.CANCELLED, s.getStatus());
            assertFalse(s.isAutoRenew());
            assertNotNull(s.getCancelledAt());
            assertFalse(s.isActive());
        }

        @Test @DisplayName("Should renew subscription")
        void shouldRenew() {
            Subscription s = Subscription.create(UUID.randomUUID(), UUID.randomUUID(), SubscriptionPlan.PREMIUM);
            s.cancel();
            s.renew();
            assertEquals(SubscriptionStatus.ACTIVE, s.getStatus());
            assertTrue(s.isActive());
        }

        @Test @DisplayName("Should upgrade plan")
        void shouldUpgrade() {
            Subscription s = Subscription.create(UUID.randomUUID(), UUID.randomUUID(), SubscriptionPlan.PREMIUM);
            s.upgrade(SubscriptionPlan.PREMIUM_PLUS);
            assertEquals(SubscriptionPlan.PREMIUM_PLUS, s.getPlan());
        }

        @Test @DisplayName("Should expire subscription")
        void shouldExpire() {
            Subscription s = Subscription.create(UUID.randomUUID(), UUID.randomUUID(), SubscriptionPlan.PREMIUM);
            s.expire();
            assertEquals(SubscriptionStatus.EXPIRED, s.getStatus());
            assertFalse(s.isActive());
        }
    }

    @Nested @DisplayName("Payment")
    class PaymentTests {
        @Test @DisplayName("Should create with PENDING status")
        void shouldCreatePending() {
            Payment p = Payment.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    BigDecimal.valueOf(9.99), "USD", "CARD");
            assertEquals(PaymentStatus.PENDING, p.getStatus());
            assertEquals(BigDecimal.valueOf(9.99), p.getAmount());
            assertEquals("USD", p.getCurrency());
        }

        @Test @DisplayName("Should complete payment")
        void shouldComplete() {
            Payment p = Payment.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    BigDecimal.TEN, "INR", "UPI");
            p.complete("txn_123");
            assertEquals(PaymentStatus.COMPLETED, p.getStatus());
            assertEquals("txn_123", p.getProviderTransactionId());
        }

        @Test @DisplayName("Should fail payment")
        void shouldFail() {
            Payment p = Payment.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    BigDecimal.TEN, "INR", "CARD");
            p.fail();
            assertEquals(PaymentStatus.FAILED, p.getStatus());
        }

        @Test @DisplayName("Should refund payment")
        void shouldRefund() {
            Payment p = Payment.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    BigDecimal.TEN, "INR", "CARD");
            p.complete("txn_456");
            p.refund();
            assertEquals(PaymentStatus.REFUNDED, p.getStatus());
        }
    }
}
