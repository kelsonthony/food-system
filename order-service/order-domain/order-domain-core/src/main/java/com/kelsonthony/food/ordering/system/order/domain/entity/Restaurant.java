package com.kelsonthony.food.ordering.system.order.domain.entity;

import com.kelsonthony.food.ordering.system.domain.entity.AggregateRoot;
import com.kelsonthony.food.ordering.system.domain.valuesobjects.RestauranteId;

import java.util.List;

public class Restaurant extends AggregateRoot<RestauranteId> {
    private final List<Product> products;
    private boolean active;

    private Restaurant(Builder builder) {
        super.setId(builder.restauranteId);
        products = builder.products;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private RestauranteId restauranteId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder restauranteId(RestauranteId val) {
            restauranteId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
