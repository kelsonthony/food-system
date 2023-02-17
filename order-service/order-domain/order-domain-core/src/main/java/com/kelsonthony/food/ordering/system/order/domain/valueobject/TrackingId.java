package com.kelsonthony.food.ordering.system.order.domain.valueobject;

import com.kelsonthony.food.ordering.system.domain.valuesobjects.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
