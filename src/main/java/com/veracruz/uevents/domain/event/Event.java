package com.veracruz.uevents.domain.event;

import com.veracruz.uevents.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "event")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;
    private String imageUrl;
    private String eventUrl;
    private Boolean remote;
    private Date date;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private Address address;

    public Event(String title, String description, String imageUrl, String eventUrl, Boolean remote, Date date) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.eventUrl = eventUrl;
        this.remote = remote;
        this.date = date;
    }

}
