package org.brycom.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.brycom.repository.CustomerRepository;
import org.brycom.repository.GenericDAO;
import org.brycom.entities.CustomerEntity;
import org.brycom.entities.MeetingEntity;
import org.brycom.mapper.MeetingEntityMapper;
import org.brycom.valueobject.Customer;
import org.brycom.valueobject.EventsGroup;
import org.brycom.valueobject.MeetEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerEventsStoreService {

    private final GenericDAO genericDAO;
    private final MeetingEntityMapper meetingEntityMapper;
    private final CustomerRepository customerRepository;

    @Transactional
    public void storeEvents(EventsGroup eventsGroup) {
        saveValidMeetings(eventsGroup.getValidEvents());
        saveInvalidMeetings(eventsGroup.getInvalidEvents());
    }

    private void saveInvalidMeetings(List<MeetEvent> invalidEvents) {
        if (invalidEvents.isEmpty()) {
            return;
        }

        genericDAO.saveInBatch(
                invalidEvents.stream()
                        .map(meetingEntityMapper::mapToEntity)
                        .toList()
        );
    }

    private void saveValidMeetings(List<MeetEvent> validEvents) {
        if (validEvents.isEmpty()) {
            return;
        }

        Map<String, CustomerEntity> existingCustomersByPhoneNumber = findExistingCustomersForIncomingEvents(validEvents);

        List<MeetingEntity> meetings = validEvents.stream()
                .map(meetingEntityMapper::mapToEntity)
                .toList();

        meetings.forEach(m -> m.setCustomer(
                Optional.of(m.getCustomer())
                        .map(CustomerEntity::getPhoneNumber)
                        .map(existingCustomersByPhoneNumber::get)
                        .orElse(m.getCustomer())
        ));

        genericDAO.saveInBatch(meetings);
    }

    private Map<String, CustomerEntity> findExistingCustomersForIncomingEvents(List<MeetEvent> validEvents) {
        return customerRepository.findByPhoneNumbers(
                validEvents.stream()
                        .map(MeetEvent::getCustomer)
                        .map(Customer::getPhoneNumber)
                        .toList()
        ).stream().collect(Collectors.toMap(CustomerEntity::getPhoneNumber, Function.identity()));
    }
}
