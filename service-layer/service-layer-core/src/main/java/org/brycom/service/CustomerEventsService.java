package org.brycom.service;


import lombok.RequiredArgsConstructor;
import org.brycom.mapper.MeetEventsMapper;
import org.brycom.store.CustomerEventsStoreService;
import org.brycom.valueobject.CalendarEvent;
import org.brycom.valueobject.EventsGroup;
import org.brycom.valueobject.MeetEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerEventsService {
    private final MeetEventsMapper meetEventsMapper;
    private final CalendarEventsProvider eventsProvider;
    private final CustomerEventsStoreService customerEventsStoreService;

    public void collectAndStoreCustomerEvents() {
        List<MeetEvent> events = getEvents();
        customerEventsStoreService.storeAllEvents(new EventsGroup(events));
    }

    public List<MeetEvent> getEvents() {
        List<CalendarEvent> calendarEvents = eventsProvider.get();
        return calendarEvents.stream()
                .map(meetEventsMapper::calendarToMeetEvent)
                .map(meetEvent -> MeetEventsValidator.isValid(meetEvent) ? meetEvent : meetEvent.invalid())
                .toList();
    }
}
