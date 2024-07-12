package org.brycom.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.brycom.dao.CustomerRepository;
import org.brycom.dao.GenericDAO;
import org.brycom.entities.CustomerEntity;
import org.brycom.entities.MeetingEntity;
import org.brycom.mapper.MeetingEntityMapper;
import org.brycom.store.CustomerEventsStoreService;
import org.brycom.valueobject.EventsGroup;
import org.brycom.valueobject.MeetEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        genericDAO.saveInBatch(
                invalidEvents.stream()
                        .map(meetingEntityMapper::mapToEntity)
                        .toList()
        );
    }

    private void saveValidMeetings(List<MeetEvent> validEvents) {
        List<MeetingEntity> meetings = validEvents.stream()
                .map(meetingEntityMapper::mapToEntity)
                .toList();

        List<CustomerEntity> existingCustomers = customerRepository.findByPhoneNumbers(
                meetings.stream()
                        .map(MeetingEntity::getCustomer)
                        .map(CustomerEntity::getPhoneNumber)
                        .toList()
        );

        List<CustomerEntity> customersToSave = new ArrayList<>();
        for (MeetingEntity meetingEntity : meetings) {
            if (existingCustomers.contains(meetingEntity.getCustomer())) {
                meetingEntity.setCustomer(
                        existingCustomers.stream()
                                .filter(meetingEntity.getCustomer()::equals)
                                .findFirst()
                                .get()
                );
            } else {
                customersToSave.add(meetingEntity.getCustomer());
            }
        }

        genericDAO.saveInBatch(customersToSave);
        genericDAO.saveInBatch(meetings);
    }
}
