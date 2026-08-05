package com.storyverse.infrastructure.event;

import com.storyverse.shared.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * In-process event publisher using Spring's ApplicationEventPublisher.
 * Used for domain events within the same bounded context.
 * Integration events should use the Kafka-based publisher (outbox pattern).
 */
@Component
public class SpringEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(SpringEventPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        log.debug("Publishing domain event: {} [aggregate={}]", event.getEventType(), event.getAggregateId());
        applicationEventPublisher.publishEvent(event);
    }
}
