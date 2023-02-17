package com.kelsonthony.food.ordering.system.order.service.domain.ports.inputs.messages.listener.restaurantapproval;

import com.kelsonthony.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
