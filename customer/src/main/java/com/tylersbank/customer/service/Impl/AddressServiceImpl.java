package com.tylersbank.customer.service.Impl;

import com.tylersbank.customer.domain.Address;
import com.tylersbank.customer.dto.AddressDto;
import com.tylersbank.customer.mapper.AddressMapper;
import com.tylersbank.customer.repository.AddressRepository;
import com.tylersbank.customer.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public String saveAddress(AddressDto addressDto) {
        Address address = AddressMapper.mapToAddress(addressDto);
        addressRepository.save(address);
        return address.getAddressId();
    }
}
