package com.veracruz.uevents.service;

import com.amazonaws.services.s3.AmazonS3;
import com.veracruz.uevents.domain.coupon.Coupon;
import com.veracruz.uevents.domain.event.Event;
import com.veracruz.uevents.domain.event.EventDetailsDTO;
import com.veracruz.uevents.domain.event.EventRequestDTO;
import com.veracruz.uevents.domain.event.EventResponseDTO;
import com.veracruz.uevents.domain.event.exceptions.EventNotFoundException;
import com.veracruz.uevents.repositories.EventRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EventService {
    @Value("${aws.bucket.name}")
    private String bucketName;

    private final EventRepository eventRepository;
    private final AddressService addressService;
    private final EventCouponService eventCouponService;
    private final AmazonS3 s3Client;

    public Event createEvent(EventRequestDTO data) {
        String imgUrl = null;

        if(data.image() != null) {
            imgUrl = this.uploadImage(data.image());
        }

        Event newEvent = new Event(
            data.title(),
            data.description(),
            imgUrl,
            data.eventUrl(),
            data.remote(),
            new Date(data.date())
        );

        eventRepository.save(newEvent);

        if(!data.remote()) {
            this.addressService.createAddress(data, newEvent);
        }

        return newEvent;
    }

    public Optional<Event> findById(UUID eventId) {
        return this.eventRepository.findById(eventId);
    }

    public EventDetailsDTO getEventDetails(UUID eventId) {
        Event event = this.findById(eventId)
            .orElseThrow(EventNotFoundException::new);

        List<Coupon> coupons = this.eventCouponService.getCouponsForEvent(eventId, new Date());

        List<EventDetailsDTO.CouponDTO> couponDTOS = coupons
            .stream()
            .map(coupon -> new EventDetailsDTO.CouponDTO(
                coupon.getCode(),
                coupon.getDiscount(),
                coupon.getValidUntil()))
            .toList();

        return new EventDetailsDTO(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getImageUrl(),
            event.getEventUrl(),
            event.getDate(),
            event.getRemote(),
            event.getAddress() == null ? "" : event.getAddress().getCountry(),
            event.getAddress() == null ? "" : event.getAddress().getCity(),
            event.getAddress() == null ? "" : event.getAddress().getState(),
            couponDTOS
        );
    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findUpcoming(new Date(), pageable);

        return eventsPage.map(event -> new EventResponseDTO(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getImageUrl(),
            event.getEventUrl(),
            event.getDate(),
            event.getRemote(),
            event.getAddress() != null ? event.getAddress().getCountry() : "",
            event.getAddress() != null ? event.getAddress().getCity() : "",
            event.getAddress() != null ? event.getAddress().getState() : ""
        )).stream().toList();
    }

    public List<EventResponseDTO> getFilteredEvents(
        int page, int size, String title, String country,
        String city, String state, Long startDate, Long endDate
    ) {
        title = (title != null) ? title : "";
        country = (country != null) ? country : "";
        city = (city != null) ? city : "";
        state = (state != null) ? state : "";

        startDate = (startDate == null) ? new Date().getTime() : startDate;
        endDate = (endDate == null)
            ? LocalDateTime.now().plusYears(10).toInstant(ZoneOffset.UTC).toEpochMilli()
            : endDate;

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findFiltered(
            title, country, city, state,
            new Date(startDate), new Date(endDate),
            pageable
        );

        return eventsPage.map(event -> new EventResponseDTO(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getImageUrl(),
            event.getEventUrl(),
            event.getDate(),
            event.getRemote(),
            event.getAddress() != null ? event.getAddress().getCountry() : "",
            event.getAddress() != null ? event.getAddress().getCity() : "",
            event.getAddress() != null ? event.getAddress().getState() : ""
        )).stream().toList();
    }

    private String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            File file = this.convertMultipartToFile(multipartFile);
            s3Client.putObject(bucketName, fileName, file);
            file.delete();
            return s3Client.getUrl(bucketName, fileName).toString();

        } catch (Exception e) {
            System.out.println("Error while uploading file: " + e);
            return null;
        }
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return convFile;
    }
}
