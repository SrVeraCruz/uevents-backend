package com.veracruz.uevents.domain.address;

import com.veracruz.uevents.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "address")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    private UUID id;

    private String country;
    private String city;
    private String state;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Address(String country, String city, String state, Event event) {
        this.country = country;
        this.city = city;
        this.state = state;
        this.event = event;
    }
}
