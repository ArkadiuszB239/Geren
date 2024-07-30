package org.brycom.controller;

import lombok.RequiredArgsConstructor;
import org.brycom.service.CustomerEventsCollectingService;
import org.brycom.service.CustomerEventsProcessingService;
import org.brycom.valueobject.EventsGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventsController {

    private final CustomerEventsCollectingService customerEventsCollectingService;
    private final CustomerEventsProcessingService customerEventsProcessingService;

    @GetMapping("/events")
    public ResponseEntity<EventsGroup> getTodayEvents() {
        customerEventsCollectingService.collectAndStoreCustomerEvents();
        return ResponseEntity.ok(
                customerEventsCollectingService.getEvents()
        );
    }

    @PostMapping("/events/process")
    public ResponseEntity<Void> processTodayEvents() {
        customerEventsProcessingService.processValidEvents();
        return ResponseEntity.ok().build();
    }
}
