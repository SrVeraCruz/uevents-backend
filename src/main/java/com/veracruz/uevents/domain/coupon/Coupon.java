package com.veracruz.uevents.domain.coupon;

import com.veracruz.uevents.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "coupon")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    private Integer discount;
    private Date validUntil;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Coupon(String code, Integer discount, Date validUntil, Event event) {
        this.code = code;
        this.discount = discount;
        this.validUntil = validUntil;
        this.event = event;
    }
}
