package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Address;
import com.vodafone.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/customer/{customerId}/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<Set<Address>> getAllCustomerAddresses(@PathVariable(name = "customerId") Long customerId) {
        return new ResponseEntity<>(addressService.getAllAddressesByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping(value = "/{addressId}")
    public ResponseEntity<Address> getCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                                            @PathVariable("addressId") Long addressId) {
        return new ResponseEntity<>(addressService.getAddressById(customerId, addressId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Address> addCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                                            @RequestBody Address address) {
        return new ResponseEntity<>(addressService.addAddress(customerId, address), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{addressId}")
    public ResponseEntity<Address> updateCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                                               @PathVariable("addressId") Long addressId,
                                                               @RequestBody Address address) {
        return new ResponseEntity<>(addressService.updateAddress(customerId, addressId, address), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{addressId}")
    public ResponseEntity<Address> deleteAddress(@PathVariable(name = "customerId") Long customerId,
                                                               @PathVariable("addressId") Long addressId) {
        addressService.deleteAddress(customerId, addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
