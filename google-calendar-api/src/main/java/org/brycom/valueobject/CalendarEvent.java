package org.brycom.valueobject;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.Getter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class CalendarEvent {
    private final String calendarId;
    private final String calendarName;
    private final String summary;
    private final String description;
    private final OffsetDateTime eventStartDate;
    private final String eventId;

    public CalendarEvent(Event event, String calendarId, String calendarName) {
        this.calendarId = calendarId;
        this.calendarName = calendarName;
        this.summary = event.getSummary();
        this.description = event.getDescription();
        this.eventStartDate = toOffsetDateTime(event.getStart());
        this.eventId = event.getId();
    }

    private OffsetDateTime toOffsetDateTime(EventDateTime eventDateTime) {
        Date date = new Date(eventDateTime.getDateTime().getValue());
        Instant dateInstant = date.toInstant();
        return OffsetDateTime.ofInstant(dateInstant, ZoneId.of(eventDateTime.getTimeZone()));
    }
}
