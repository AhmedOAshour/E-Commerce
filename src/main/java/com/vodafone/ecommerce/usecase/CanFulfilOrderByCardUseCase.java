package com.vodafone.ecommerce.usecase;

import com.vodafone.ecommerce.model.PaymentRequest;
import com.vodafone.ecommerce.util.NetworkHandler;

public class CanFulfilOrderByCardUseCase {

    private NetworkHandler networkHandler;
    public CanFulfilOrderByCardUseCase(NetworkHandler networkHandler)
    {
        this.networkHandler = networkHandler;
    }

    public boolean canFulfilOrder(PaymentRequest paymentRequest)
    {
        return networkHandler.checkout(paymentRequest);
    }
}
