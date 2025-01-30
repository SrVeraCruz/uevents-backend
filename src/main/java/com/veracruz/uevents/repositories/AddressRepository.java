package com.veracruz.uevents.repositories;

import com.veracruz.uevents.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
