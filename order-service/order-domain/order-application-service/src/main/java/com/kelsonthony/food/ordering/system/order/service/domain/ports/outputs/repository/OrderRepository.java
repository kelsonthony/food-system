package com.kelsonthony.food.ordering.system.order.service.domain.ports.outputs.repository;

import com.kelsonthony.food.ordering.system.order.domain.entity.Order;
import com.kelsonthony.food.ordering.system.order.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
