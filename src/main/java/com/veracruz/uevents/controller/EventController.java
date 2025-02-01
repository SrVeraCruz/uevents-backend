package com.veracruz.uevents.controller;

import com.veracruz.uevents.domain.event.Event;
import com.veracruz.uevents.domain.event.EventDetailsDTO;
import com.veracruz.uevents.domain.event.EventRequestDTO;
import com.veracruz.uevents.domain.event.EventResponseDTO;
import com.veracruz.uevents.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {
    private final EventService service;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(
        @RequestParam("title") String title,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "image", required = false) MultipartFile image,
        @RequestParam("eventUrl") String eventUrl,
        @RequestParam("date") Long date,
        @RequestParam("remote") Boolean remote,
        @RequestParam("country") String country,
        @RequestParam("city") String city,
        @RequestParam("state") String state
    ) {
        EventRequestDTO eventRequestDTO = new EventRequestDTO(
            title,description, image, eventUrl,
            date, remote, country, city, state
        );
        Event newEvent = this.service.createEvent(eventRequestDTO);
        return ResponseEntity.ok(newEvent);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getUpcomingEvents(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        List<EventResponseDTO> events = this.service.getUpcomingEvents(page, size);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable("eventId") UUID eventId) {
        EventDetailsDTO eventDetails = this.service.getEventDetails(eventId);
        return ResponseEntity.ok(eventDetails);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>> getFilteredEvents(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "country", required = false) String country,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "startDate", required = false) Long startDate,
        @RequestParam(value = "endDate", required = false) Long endDate
    ) {
        List<EventResponseDTO> events = this.service.getFilteredEvents(
            page, size, title, country,
            city, state, startDate, endDate
        );
        return ResponseEntity.ok(events);
    }
}
