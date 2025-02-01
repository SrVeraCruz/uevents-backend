package com.veracruz.uevents.service;

import com.veracruz.uevents.domain.coupon.Coupon;
import com.veracruz.uevents.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {
    public final CouponRepository couponRepository;

    public Coupon save(Coupon coupon) {
        return this.couponRepository.save(coupon);
    }

    public List<Coupon> consult(UUID eventId, Date validUntil) {
        return this.couponRepository.findValidCoupons(eventId, validUntil);
    }
}