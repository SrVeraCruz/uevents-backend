package com.veracruz.uevents.domain.coupon;

public record CouponRequestDTO(String code, Integer discount, Long validUntil) {
}
