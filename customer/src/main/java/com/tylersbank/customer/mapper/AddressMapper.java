package com.tylersbank.customer.mapper;

import com.tylersbank.customer.domain.Address;
import com.tylersbank.customer.dto.AddressDto;

public class AddressMapper {

    public static Address mapToAddress(AddressDto addressDto) {
        return Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .postalCode(addressDto.getPostalCode())
                .country(addressDto.getCountry())
                .build();
    }
}
