
package com.vodafone.soap_consumer;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CardValidationWebService", targetNamespace = "http://service.vodafone.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface CardValidationWebService {


    /**
     * 
     * @param pin
     * @param expireMonth
     * @param expireYear
     * @param cardNumber
     * @return
     *     returns boolean
     */
    @WebMethod(operationName = "IsValid", action = "http://service.vodafone.com/CardValidation/IsValid")
    @WebResult(name = "IsValidResponse", partName = "IsValidResponse")
    @Action(input = "http://service.vodafone.com/CardValidation/IsValid", output = "http://service.vodafone.com/CardValidationWebService/IsValidResponse")
    public boolean isValid(
        @WebParam(name = "cardNumber", partName = "cardNumber")
        String cardNumber,
        @WebParam(name = "pin", partName = "pin")
        String pin,
        @WebParam(name = "expireMonth", partName = "expireMonth")
        String expireMonth,
        @WebParam(name = "expireYear", partName = "expireYear")
        String expireYear);

}
