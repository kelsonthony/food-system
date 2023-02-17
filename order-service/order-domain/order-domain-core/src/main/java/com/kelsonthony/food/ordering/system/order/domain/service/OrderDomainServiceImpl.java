package com.kelsonthony.food.ordering.system.order.domain.service;

import com.kelsonthony.food.ordering.system.order.domain.entity.Order;
import com.kelsonthony.food.ordering.system.order.domain.entity.Product;
import com.kelsonthony.food.ordering.system.order.domain.entity.Restaurant;
import com.kelsonthony.food.ordering.system.order.domain.event.OrderCancelledEvent;
import com.kelsonthony.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.kelsonthony.food.ordering.system.order.domain.event.OrderPaidEvent;
import com.kelsonthony.food.ordering.system.order.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    private static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: '{}' is initiated: ", order.getId().getValue());
        return new OrderCreatedEvent(order, getUtc());
    }


    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: '{}' is paid", order.getId().getValue());
        return new OrderPaidEvent(order, getUtc());
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: '{}' is approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failture) {
        order.initCancel(failture);
        log.info("Order payment is cancelling for order id: '{}'", order.getId().getValue());
        return new OrderCancelledEvent(order, getUtc());
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMesssages) {
        order.cancel(failureMesssages);
        log.info("Order with id: '{}' is cancelled", order.getId().getValue());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant with id " + restaurant.getId().getValue() +
                    " is currently not active!");
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct -> {
            Product currentProduct = orderItem.getProduct();
            if (currentProduct.equals(restaurantProduct)) {
                currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),
                        restaurantProduct.getPrice());
            }
        }));
    }

    private static ZonedDateTime getUtc() {
        return ZonedDateTime.now(ZoneId.of(UTC));
    }
}
