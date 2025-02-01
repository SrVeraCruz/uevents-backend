package com.veracruz.uevents.service;

import com.veracruz.uevents.domain.coupon.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventCouponService {
    private final CouponService couponService;

    public List<Coupon> getCouponsForEvent(UUID eventId, Date validUntil) {
        return couponService.consult(eventId, validUntil);
    }


}
