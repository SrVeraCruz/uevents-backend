package com.veracruz.uevents.repositories;

import com.veracruz.uevents.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    @Query("SELECT c FROM Coupon c WHERE c.event.id = :eventId AND c.validUntil >= :validUntil")
    List<Coupon> findValidCoupons(@Param("eventId") UUID eventId, @Param("validUntil") Date validUntil);
}
