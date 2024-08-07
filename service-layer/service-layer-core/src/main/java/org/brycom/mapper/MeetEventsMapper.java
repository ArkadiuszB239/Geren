package org.brycom.mapper;

import org.brycom.valueobject.CalendarEvent;
import org.brycom.valueobject.MeetEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeetEventsMapper {

    @Mapping(target = "customer.name", source = "summary")
    @Mapping(target = "customer.phoneNumber", source = "description")
    @Mapping(target = "startTime", source = "eventStartDate")
    @Mapping(target = "phoneNumber", source = "description")
    @Mapping(target = "sourceCalendar", source = "calendarName")
    @Mapping(target = "calendarEventId", source = "eventId")
    @Mapping(target = "meetingId", ignore = true)
    MeetEvent calendarToMeetEvent(CalendarEvent calendarEvent);
}