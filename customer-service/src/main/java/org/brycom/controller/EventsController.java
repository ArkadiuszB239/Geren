package org.brycom.controller;

import lombok.RequiredArgsConstructor;
import org.brycom.service.CustomerEventsCollectingService;
import org.brycom.service.CustomerEventsProcessingService;
import org.brycom.valueobject.SelectionPeriod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventsController {

    private final CustomerEventsCollectingService customerEventsCollectingService;
    private final CustomerEventsProcessingService customerEventsProcessingService;

    @PostMapping("/collect")
    public ResponseEntity<Void> getTodayEvents() {
        customerEventsCollectingService.collectAndStoreCustomerEvents(SelectionPeriod.DAY);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/process")
    public ResponseEntity<Void> processTodayEvents() {
        customerEventsProcessingService.processValidEvents();
        return ResponseEntity.ok().build();
    }
}
