package com.veracruz.uevents.domain.event;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record EventRequestDTO(
    String title,
    String description,
    MultipartFile image,
    String eventUrl,
    Long date,
    Boolean remote,
    String country,
    String city,
    String state
) { }
