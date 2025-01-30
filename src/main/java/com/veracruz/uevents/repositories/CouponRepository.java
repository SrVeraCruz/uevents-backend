package com.veracruz.uevents.repositories;

import com.veracruz.uevents.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
}
