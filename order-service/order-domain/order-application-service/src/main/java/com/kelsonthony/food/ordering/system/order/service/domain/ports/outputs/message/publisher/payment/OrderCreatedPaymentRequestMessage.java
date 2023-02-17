package com.kelsonthony.food.ordering.system.order.service.domain.ports.outputs.message.publisher.payment;

import com.kelsonthony.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.kelsonthony.food.ordering.system.order.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessage extends DomainEventPublisher<OrderCreatedEvent> {
}
