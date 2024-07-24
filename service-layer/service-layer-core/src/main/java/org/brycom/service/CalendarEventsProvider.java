package org.brycom.service;

import lombok.RequiredArgsConstructor;
import org.brycom.exception.ResourceUnavailableException;
import org.brycom.valueobject.CalendarEvent;
import org.brycom.valueobject.EventSearchRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarEventsProvider {

    private final CalendarService calendarService;
    private final CalendarEventSearchRequestProvider searchRequestProvider;

    public List<CalendarEvent> get() {
        List<EventSearchRequest> searchRequests = searchRequestProvider.get();
        return searchRequests.stream()
                .map(this::getCalendarEvents)
                .flatMap(List::stream)
                .toList();
    }

    private List<CalendarEvent> getCalendarEvents(EventSearchRequest searchRequest) {
        try {
            return calendarService.getCalendarEvents(searchRequest);
        } catch (IOException e) {
            throw new ResourceUnavailableException("Google calendar events listing error for calendar id: " + searchRequest.getCalendarId(), e);
        }
    }
}
