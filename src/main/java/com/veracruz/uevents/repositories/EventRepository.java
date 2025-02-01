package com.veracruz.uevents.repositories;

import com.veracruz.uevents.domain.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e LEFT JOIN e.address a WHERE e.date >= :currentDate")
    public Page<Event> findUpcoming(@Param("currentDate") Date currentDate, Pageable pageable);

    @Query("SELECT e FROM Event e " +
        "LEFT JOIN e.address a " +
        "WHERE e.title LIKE %:title% AND " +
        "a.country LIKE %:country% AND " +
        "a.city LIKE %:city% AND " +
       "a.state LIKE %:state% AND " +
        "(e.date BETWEEN :startDate AND :endDate)")
    public Page<Event> findFiltered(
        @Param("title") String title,
        @Param("country") String country,
        @Param("city") String city,
        @Param("state") String state,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        Pageable pageable
    );
}
