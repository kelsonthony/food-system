package com.kelsonthony.food.ordering.system.order.domain.valueobject;

import com.kelsonthony.food.ordering.system.domain.valuesobjects.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
