package com.veracruz.uevents.domain.event;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventDetailsDTO(
    UUID id,
    String title,
    String description,
    String imageUrl,
    String eventUrl,
    Date date,
    Boolean remote,
    String country,
    String city,
    String state,
    List<CouponDTO> coupons
) {

    public record CouponDTO(
       String code,
       Integer discount,
       Date validUntil
    ) {}
}
