package org.brycom.mapper;

import org.brycom.entities.CustomerEntity;
import org.brycom.entities.MeetingEntity;
import org.brycom.valueobject.Customer;
import org.brycom.valueobject.MeetEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface MeetingEntityMapper {
    @Mapping(source = "startTime", target = "meetingDay")
    @Mapping(source = "calendarEventId", target = "eventId")
    MeetingEntity mapToEntity(MeetEvent meetEvent);

    @Mapping(source = "id", target="meetingId")
    @Mapping(source = "eventId", target = "calendarEventId")
    @Mapping(target = "startTime", expression = "java(atZoneSameInstant(meetingEntity.getStartTime()))")
    MeetEvent mapToEvent(MeetingEntity meetingEntity);

    CustomerEntity mapToCustomerEntity(Customer customer);

    default LocalDate toLocalDate(OffsetDateTime value) {
        return value.toLocalDate();
    }

    default OffsetDateTime atZoneSameInstant(OffsetDateTime value) {
        return value.atZoneSameInstant(ZoneId.systemDefault()).toOffsetDateTime();
    }
}
