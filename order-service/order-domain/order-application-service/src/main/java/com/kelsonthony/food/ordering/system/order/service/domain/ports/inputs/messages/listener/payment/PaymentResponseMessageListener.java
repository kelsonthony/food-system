package com.kelsonthony.food.ordering.system.order.service.domain.ports.inputs.messages.listener.payment;

import com.kelsonthony.food.ordering.system.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
