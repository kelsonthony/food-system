package com.kelsonthony.food.ordering.system.order.service.domain.ports.outputs.message.publisher.restaurantapproval;

import com.kelsonthony.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.kelsonthony.food.ordering.system.order.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
