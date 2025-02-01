package com.veracruz.uevents.controller;

import com.veracruz.uevents.domain.coupon.CouponRequestDTO;
import com.veracruz.uevents.domain.coupon.CouponResponseDTO;
import com.veracruz.uevents.service.CouponEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class CouponController {
    private final CouponEventService couponEventService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<CouponResponseDTO> addCouponToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data) {
        CouponResponseDTO coupon = couponEventService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupon);
    }
}
