package com.kelsonthony.food.ordering.system.domain.valuesobjects;

import java.util.UUID;

public class CustomerId extends BaseId<UUID> {
    public CustomerId(UUID value) {
        super(value);
    }
}
