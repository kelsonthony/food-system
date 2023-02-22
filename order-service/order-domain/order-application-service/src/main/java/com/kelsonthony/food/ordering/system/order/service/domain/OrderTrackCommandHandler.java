package com.kelsonthony.food.ordering.system.order.service.domain;

import com.kelsonthony.food.ordering.system.order.domain.entity.Order;
import com.kelsonthony.food.ordering.system.order.domain.exception.OrderNotFoundException;
import com.kelsonthony.food.ordering.system.order.domain.valueobject.TrackingId;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.kelsonthony.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.kelsonthony.food.ordering.system.order.service.domain.ports.outputs.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;

    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> orderResult =
                orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));

        if (orderResult.isEmpty()) {
            log.warn("Could not fund order with tracking id: '{}'", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not fund order with tracking id: '{}'"
                    + trackOrderQuery.getOrderTrackingId());
        }

        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
    }
}
