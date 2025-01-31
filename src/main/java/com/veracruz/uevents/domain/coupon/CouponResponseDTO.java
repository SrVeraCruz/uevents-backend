package com.veracruz.uevents.domain.coupon;

import java.util.Date;
import java.util.UUID;

public record CouponResponseDTO(
    UUID id,
    String code,
    Integer discount,
    Date validUntil
) {
    public static CouponResponseDTO fromEntity(Coupon coupon) {
        return new CouponResponseDTO(
            coupon.getId(),
            coupon.getCode(),
            coupon.getDiscount(),
            coupon.getValidUntil()
        );
    }
}