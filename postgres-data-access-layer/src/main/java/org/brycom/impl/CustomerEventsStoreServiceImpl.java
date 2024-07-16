package org.brycom.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.brycom.dao.CustomerRepository;
import org.brycom.dao.GenericDAO;
import org.brycom.entities.CustomerEntity;
import org.brycom.entities.MeetingEntity;
import org.brycom.mapper.MeetingEntityMapper;
import org.brycom.store.CustomerEventsStoreService;
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
public class CustomerEventsStoreServiceImpl implements CustomerEventsStoreService {

    private final GenericDAO genericDAO;
    private final CustomerRepository customerRepository;
    private final MeetingEntityMapper meetingEntityMapper;

    @Override
    @Transactional
    public void storeAllEvents(EventsGroup meetEventsGroup) {
        saveValidMeetings(meetEventsGroup.getValidEvents());
        saveInvalidMeetings(meetEventsGroup.getInvalidEvents());
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

        Map<String, CustomerEntity> existingCustomersByPhoneNumber = customerRepository.findByPhoneNumbers(
                validEvents.stream()
                        .map(MeetEvent::getCustomer)
                        .map(Customer::getPhoneNumber)
                        .toList()
        ).stream().collect(Collectors.toMap(CustomerEntity::getPhoneNumber, Function.identity()));

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
}
