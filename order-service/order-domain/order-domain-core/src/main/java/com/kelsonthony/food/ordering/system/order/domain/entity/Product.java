package com.kelsonthony.food.ordering.system.order.domain.entity;

import com.kelsonthony.food.ordering.system.domain.entity.BaseEntity;
import com.kelsonthony.food.ordering.system.domain.valuesobjects.Money;
import com.kelsonthony.food.ordering.system.domain.valuesobjects.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId) {
        super.setId(productId);

    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
