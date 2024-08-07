package org.brycom.service;

import lombok.RequiredArgsConstructor;
import org.brycom.config.CalendarEventsCollectingConfig;
import org.brycom.exception.ResourceUnavailableException;
import org.brycom.valueobject.EventSearchRequest;
import org.brycom.valueobject.SelectionPeriod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalendarEventSearchRequestProvider {
    private static final String OWNER_CALENDAR_ID = "primary";

    private final CalendarService calendarService;
    private final CalendarEventsCollectingConfig collectingConfig;

    public List<EventSearchRequest> get(SelectionPeriod selectionPeriod) {
        List<String> calendarIDs = getCalendarIds();

        return calendarIDs.stream()
                .map(calendarId -> createSearchRequest(calendarId, selectionPeriod))
                .toList();
    }

    private List<String> getCalendarIds() {
        List<String> calendarNames = getCalendarNames();
        List<String> calendarIds;
        try {
            calendarIds = new ArrayList<>(calendarService.getCalendarIds(calendarNames));
        } catch (IOException e) {
            throw new ResourceUnavailableException("Calendar ids extraction error", e);
        }

        if (isOwnerCalendarPresent()) {
            calendarIds.add(OWNER_CALENDAR_ID);
        }
        return calendarIds;
    }

    private List<String> getCalendarNames() {
        return collectingConfig.getCalendars()
                .stream()
                .filter(c -> !OWNER_CALENDAR_ID.equals(c))
                .toList();
    }

    private boolean isOwnerCalendarPresent() {
        return collectingConfig.getCalendars().stream().anyMatch(OWNER_CALENDAR_ID::equals);
    }

    private EventSearchRequest createSearchRequest(String calendarId, SelectionPeriod selectionPeriod) {
        return new EventSearchRequest(
                calendarId,
                selectionPeriod.getPeriodStart(),
                selectionPeriod.getPeriodEnd()
        );
    }
}
