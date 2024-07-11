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
    MeetEvent calendarToMeetEvent(CalendarEvent calendarEvent);
}