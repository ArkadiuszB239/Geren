package org.brycom.mapper;

import lombok.experimental.UtilityClass;
import org.brycom.entities.CustomerEntity;
import org.brycom.entities.MeetingEntity;
import org.brycom.valueobject.Customer;
import org.brycom.valueobject.MeetEvent;

import java.util.Optional;

@UtilityClass
public class MappingUtils {
    public MeetingEntity toMeetingEntity(MeetEvent meetEvent) {
        MeetingEntity meetingEntity = new MeetingEntity();
        meetingEntity.setMeetingDay(meetEvent.getStartTime().toLocalDate());
        meetingEntity.setCustomer(Optional.ofNullable(meetEvent.getCustomer()).map(MappingUtils::toCustomerEntity).orElse(null));
        meetingEntity.setStartTime(meetEvent.getStartTime());
        meetingEntity.setNotificationState(meetEvent.getNotificationState());
        meetingEntity.setPhoneNumber(meetEvent.getPhoneNumber());
        return meetingEntity;
    }

    public CustomerEntity toCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setPhoneNumber(customer.getPhoneNumber());
        return customerEntity;
    }
}
