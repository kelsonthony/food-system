package com.kelsonthony.food.ordering.system.order.domain.event;

import com.kelsonthony.food.ordering.system.order.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
