package com.kelsonthony.food.ordering.system.order.domain.service;

import com.kelsonthony.food.ordering.system.order.domain.entity.Order;
import com.kelsonthony.food.ordering.system.order.domain.entity.Restaurant;
import com.kelsonthony.food.ordering.system.order.domain.event.OrderCancelledEvent;
import com.kelsonthony.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.kelsonthony.food.ordering.system.order.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failture);

    void cancelOrder(Order order, List<String> failureMesssages);
}
