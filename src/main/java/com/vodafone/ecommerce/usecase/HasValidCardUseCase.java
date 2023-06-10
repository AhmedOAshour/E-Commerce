package com.vodafone.ecommerce.usecase;

import com.vodafone.soap_consumer.CardValidationWebService;
import com.vodafone.soap_consumer.CardValidationWebServiceImplService;

public class HasValidCardUseCase {

    private CardValidationWebServiceImplService cardValidationWebService;
    public HasValidCardUseCase(CardValidationWebServiceImplService cardValidationWebService)
    {
        this.cardValidationWebService = cardValidationWebService;
    }

    public boolean isValidCard(String cardNumber, String pinNumber, String expirationMonth, String expirationYear)
    {
        return cardValidationWebService.getCardValidationPort().isValid(cardNumber, pinNumber, expirationMonth, expirationYear);
    }
}
