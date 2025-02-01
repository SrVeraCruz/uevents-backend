package com.veracruz.uevents.service;

import com.veracruz.uevents.domain.coupon.Coupon;
import com.veracruz.uevents.domain.coupon.CouponRequestDTO;
import com.veracruz.uevents.domain.coupon.CouponResponseDTO;
import com.veracruz.uevents.domain.event.Event;
import com.veracruz.uevents.domain.event.exceptions.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponEventService {
    private final EventService eventService;
    private final CouponService couponService;

    public CouponResponseDTO addCouponToEvent(UUID eventId, CouponRequestDTO data) {
        Event event = this.eventService
            .findById(eventId)
            .orElseThrow(EventNotFoundException::new);

        Coupon coupon  = new Coupon(data.code(), data.discount(), new Date(data.validUntil()), event);

        Coupon savedCoupon = this.couponService.save(coupon);

        return CouponResponseDTO.fromEntity(savedCoupon);
    }
}
