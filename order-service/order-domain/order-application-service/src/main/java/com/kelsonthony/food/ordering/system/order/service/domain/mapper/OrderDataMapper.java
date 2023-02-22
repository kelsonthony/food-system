package com.kelsonthony.food.ordering.system.order.service.domain.mapper;

import com.kelsonthony.food.ordering.system.domain.valuesobjects.CustomerId;
import com.kelsonthony.food.ordering.system.domain.valuesobjects.Money;
import com.kelsonthony.food.ordering.system.domain.valuesobjects.ProductId;
import com.kelsonthony.food.ordering.system.domain.valuesobjects.RestauranteId;
import com.kelsonthony.food.ordering.system.order.domain.entity.Order;
import com.kelsonthony.food.ordering.system.order.domain.entity.OrderItem;
import com.kelsonthony.food.ordering.system.order.domain.entity.Product;
import com.kelsonthony.food.ordering.system.order.domain.entity.Restaurant;
import com.kelsonthony.food.ordering.system.order.domain.valueobject.StreetAddress;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.create.OrderAdress;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.create.OrderItemCreate;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restauranteId(new RestauranteId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                                new Product(new ProductId(orderItem.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restauranteId(new RestauranteId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getOrderAdress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse  orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<OrderItemCreate> orderItems) {

        return orderItems.stream()
                .map(orderItem ->
                        OrderItem.builder()
                                .product(new Product(new ProductId(orderItem.getProductId())))
                                .price(new Money(orderItem.getPrice()))
                                .quantity(orderItem.getQuantity())
                                .subTotal(new Money(orderItem.getSubTotal()))
                                .build()).collect(Collectors.toList());

    }


    private StreetAddress orderAddressToStreetAddress(OrderAdress orderAdress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAdress.getStreet(),
                orderAdress.getPostalCode(),
                orderAdress.getCity()
        );
    }


}

