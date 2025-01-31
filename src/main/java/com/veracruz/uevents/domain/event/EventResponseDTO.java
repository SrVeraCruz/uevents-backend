package com.veracruz.uevents.domain.event;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

public record EventResponseDTO(
    UUID id,
    String title,
    String description,
    String imageUrl,
    String eventUrl,
    Date date,
    Boolean remote,
    String country,
    String city,
    String state
) { }
