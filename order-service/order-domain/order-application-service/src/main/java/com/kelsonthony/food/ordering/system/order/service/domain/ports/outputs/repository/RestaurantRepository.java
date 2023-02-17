package com.kelsonthony.food.ordering.system.order.service.domain.ports.outputs.repository;

import com.kelsonthony.food.ordering.system.order.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
