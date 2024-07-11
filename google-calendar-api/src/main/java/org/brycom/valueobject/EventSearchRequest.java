package org.brycom.valueobject;

import com.google.api.client.util.DateTime;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Getter
public class EventSearchRequest {
    private final DateTime fromDate;
    private final DateTime toDate;

    public EventSearchRequest(LocalDateTime fromDate, LocalDateTime toDate) {
        this.fromDate = toDateTime(fromDate);
        this.toDate = toDateTime(toDate);
    }

    private DateTime toDateTime(LocalDateTime dateTime) {
        Instant dateTimeInstant = dateTime.toInstant(ZoneOffset.UTC);
        Date date = Date.from(dateTimeInstant);
        return new DateTime(date);
    }
}
