package org.brycom.service;


import lombok.RequiredArgsConstructor;
import org.brycom.mapper.MeetEventsMapper;
import org.brycom.service.external.CustomerEventsService;
import org.brycom.valueobject.CalendarEvent;
import org.brycom.valueobject.EventsGroup;
import org.brycom.valueobject.MeetEvent;
import org.brycom.valueobject.SelectionPeriod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerEventsCollectingService {
    private final MeetEventsMapper meetEventsMapper;
    private final CalendarEventsProvider eventsProvider;
    private final CustomerEventsService customerEventsService;

    public void collectAndStoreCustomerEvents(SelectionPeriod selectionPeriod) {
        EventsGroup eventsGroup = getEvents(selectionPeriod);
        EventsGroup processedEvents = customerEventsService.findEvents(selectionPeriod);
        EventsGroup eventsToStore = eventsGroup.subtract(processedEvents);
        customerEventsService.storeAllEvents(eventsToStore);
    }

    private EventsGroup getEvents(SelectionPeriod selectionPeriod) {
        List<CalendarEvent> calendarEvents = eventsProvider.get(selectionPeriod);
        List<MeetEvent> events = calendarEvents.stream()
                .map(meetEventsMapper::calendarToMeetEvent)
                .map(meetEvent -> MeetEventsValidator.isValid(meetEvent) ? meetEvent.valid() : meetEvent.invalid())
                .toList();
        return new EventsGroup(events);
    }
}
