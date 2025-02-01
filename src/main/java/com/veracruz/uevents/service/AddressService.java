package com.veracruz.uevents.service;

import com.veracruz.uevents.domain.address.Address;
import com.veracruz.uevents.domain.event.Event;
import com.veracruz.uevents.domain.event.EventRequestDTO;
import com.veracruz.uevents.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    public Address createAddress(EventRequestDTO data, Event event) {
        Address address = new Address(data.country(), data.city(), data.state(), event);
        return this.repository.save(address);
    }
}
