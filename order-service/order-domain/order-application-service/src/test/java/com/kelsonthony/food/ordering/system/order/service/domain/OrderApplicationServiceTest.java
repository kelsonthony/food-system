package com.kelsonthony.food.ordering.system.order.service.domain;

import com.kelsonthony.food.ordering.system.domain.valuesobjects.*;
import com.kelsonthony.food.ordering.system.order.domain.entity.*;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.create.OrderAdress;
import com.kelsonthony.food.ordering.system.order.service.domain.dto.create.OrderItemCreate;
import com.kelsonthony.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.kelsonthony.food.ordering.system.order.service.domain.ports.inputs.service.OrderApplicationService;
import com.kelsonthony.food.ordering.system.order.service.domain.ports.outputs.repository.CustomerRepository;
import com.kelsonthony.food.ordering.system.order.service.domain.ports.outputs.repository.OrderRepository;
import com.kelsonthony.food.ordering.system.order.service.domain.ports.outputs.repository.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("359abda4-60b9-447a-b26f-11e710a98016");
    private final UUID RESTAURANT_ID = UUID.fromString("310cf746-ea96-4657-8685-f23d12c34284");
    private final UUID PRODUCT_ID = UUID.fromString("785e23dd-48a4-459a-aea5-0b6a6ec18718");
    private final UUID ORDER_ID = UUID.fromString("5b234fe2-0d19-4d52-931e-20c13607f408");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    private void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAdress(OrderAdress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItemCreate.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItemCreate.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                ))
                .build();

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAdress(OrderAdress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItemCreate.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItemCreate.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                ))
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAdress(OrderAdress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItemCreate.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItemCreate.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                ))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.builder()
                .restauranteId(new RestauranteId(createOrderCommand.getRestaurantId()))
                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1",
                        new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2",
                                new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);

        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

    }

    @Test
    public void testCreateOrder() {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        Assertions.assertEquals(createOrderResponse.getOrderStatus(), OrderStatus.PENDING);
        Assertions.assertEquals(createOrderResponse.getMessage(), "Order created successfully");
        Assertions.assertNotNull(createOrderResponse.getOrderTrackingId());
    }


}
