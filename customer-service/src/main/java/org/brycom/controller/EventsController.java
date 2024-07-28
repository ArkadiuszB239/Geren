package org.brycom.controller;

import lombok.RequiredArgsConstructor;
import org.brycom.service.CustomerEventsService;
import org.brycom.valueobject.EventsGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventsController {

    private final CustomerEventsService customerEventsService;

    @GetMapping("/events")
    public ResponseEntity<EventsGroup> getTodayEvents() {
        customerEventsService.collectAndStoreCustomerEvents();
        return ResponseEntity.ok(
                customerEventsService.getEvents()
        );
    }
}
