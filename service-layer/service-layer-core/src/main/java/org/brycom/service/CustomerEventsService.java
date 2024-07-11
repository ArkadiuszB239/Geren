package org.brycom.service;


import lombok.RequiredArgsConstructor;
import org.brycom.exception.ResourceUnavailableException;
import org.brycom.mapper.MeetEventsMapper;
import org.brycom.store.CustomerEventsStoreService;
import org.brycom.utils.DateUtils;
import org.brycom.valueobject.CalendarEvent;
import org.brycom.valueobject.EventSearchRequest;
import org.brycom.valueobject.MeetEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerEventsService {
    private final MeetEventsMapper meetEventsMapper;
    private final CalendarService calendarService;
    private final CustomerEventsStoreService customerEventsStoreService;

    public void collectAndStoreCustomerEvents() {
        List<MeetEvent> events = getCustomerEvents();
        customerEventsStoreService.storeAllEvents(events);
    }

    public List<MeetEvent> getCustomerEvents() {
        List<CalendarEvent> calendarEvents = getEvents();
        return calendarEvents.stream()
                .map(meetEventsMapper::calendarToMeetEvent)
                .map(meetEvent -> MeetEventsValidator.isValid(meetEvent) ? meetEvent : meetEvent.invalid())
                .toList();
    }

    private List<CalendarEvent> getEvents() {
        EventSearchRequest searchRequest = new EventSearchRequest(
                DateUtils.getDayStart(),
                DateUtils.getDayEnd()
        );

        try {
            return calendarService.getCalendarEvents(searchRequest);
        } catch (IOException e) {
            throw new ResourceUnavailableException("Google calendar events listing error.", e);
        }
    }
}
