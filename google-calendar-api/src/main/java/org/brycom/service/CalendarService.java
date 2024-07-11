package org.brycom.service;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.brycom.valueobject.CalendarEvent;
import org.brycom.valueobject.EventSearchRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final Calendar calendarService;

    public List<CalendarEvent> getCalendarEvents(EventSearchRequest searchRequest) throws IOException {
        Events events = calendarService.events()
                .list("primary")
                .setTimeMin(searchRequest.getFromDate())
                .setTimeMax(searchRequest.getToDate())
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        return events.getItems().stream()
                .map(CalendarEvent::new)
                .toList();
    }
}
