package org.brycom.service;

import org.brycom.config.CalendarEventsCollectingConfig;
import org.brycom.exception.ResourceUnavailableException;
import org.brycom.valueobject.EventSearchRequest;
import org.brycom.valueobject.SelectionPeriod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalendarEventSearchRequestProviderTest {

    @Mock
    private CalendarService calendarService;
    @Mock
    private CalendarEventsCollectingConfig collectingConfig;

    @InjectMocks
    private CalendarEventSearchRequestProvider requestProvider;

    @BeforeEach
    void initMocks() throws IOException {
        when(calendarService.getCalendarIds(List.of("Test calendar"))).thenReturn(List.of("1"));
        when(collectingConfig.getCalendars()).thenReturn(List.of("primary", "Test calendar"));
    }

    @Test
    void shouldReturnEventSearchWithPrimaryCalendarId() {
        List<EventSearchRequest> searchRequests = requestProvider.get(SelectionPeriod.DAY);

        assertFalse(searchRequests.isEmpty());
        assertTrue(searchRequests.stream().anyMatch(s -> "primary".equals(s.getCalendarId())));
    }

    @Test
    void shouldReturnEventSearchWithCalendarIdEqualsTo1() throws IOException {
        List<EventSearchRequest> searchRequests = requestProvider.get(SelectionPeriod.DAY);

        assertFalse(searchRequests.isEmpty());
        verify(calendarService, times(1)).getCalendarIds(List.of("Test calendar"));
        assertTrue(searchRequests.stream().anyMatch(s -> "1".equals(s.getCalendarId())));
    }

    @Test
    void shouldThrowResourcesUnavailableException() throws IOException {
        when(calendarService.getCalendarIds(List.of("Test calendar"))).thenThrow(IOException.class);

        assertThrows(ResourceUnavailableException.class, () -> requestProvider.get(SelectionPeriod.DAY));
    }


}