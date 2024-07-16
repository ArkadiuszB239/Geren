package org.brycom.impl;


import org.brycom.dao.CustomerRepository;
import org.brycom.dao.GenericDAO;
import org.brycom.entities.CustomerEntity;
import org.brycom.entities.MeetingEntity;
import org.brycom.mapper.MappingUtils;
import org.brycom.mapper.MeetingEntityMapper;
import org.brycom.valueobject.Customer;
import org.brycom.valueobject.EventsGroup;
import org.brycom.valueobject.MeetEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerEventsStoreServiceImplTest {

    @Mock
    private GenericDAO genericDAO;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MeetingEntityMapper meetingEntityMapper;

    @InjectMocks
    private CustomerEventsStoreServiceImpl customerEventsStoreService;

    @BeforeEach
    public void init() {
        when(meetingEntityMapper.mapToEntity(any(MeetEvent.class)))
                .thenAnswer(invocation -> Mappers.getMapper(MeetingEntityMapper.class).mapToEntity(invocation.getArgument(0)));
        lenient().when(customerRepository.findByPhoneNumbers(anyList())).thenReturn(List.of());
    }

    @Test
    void shouldSaveValidMeetings() {
        MeetEvent event = createEvent("554 332 152");

        customerEventsStoreService.storeAllEvents(new EventsGroup(List.of(event)));

        verify(meetingEntityMapper, times(1)).mapToEntity(any(MeetEvent.class));
        verify(customerRepository, times(1)).findByPhoneNumbers(anyList());
        verify(genericDAO, times(1)).saveInBatch(List.of(MappingUtils.toMeetingEntity(event)));
    }

    @Test
    void shouldSaveInvalidMeetings() {
        MeetEvent event = createEvent("554 332 152 22").invalid();

        customerEventsStoreService.storeAllEvents(new EventsGroup(List.of(event)));

        verify(meetingEntityMapper, times(1)).mapToEntity(any(MeetEvent.class));
        verify(customerRepository, never()).findByPhoneNumbers(anyList());
        verify(genericDAO, times(1)).saveInBatch(List.of(MappingUtils.toMeetingEntity(event)));
    }

    @Test
    void shouldSaveValidMeetingsWithExistingCustomer() {
        String phoneNumber = "554 332 152";
        Long customerId = 123L;
        MeetEvent event = createEvent(phoneNumber);
        CustomerEntity customer = MappingUtils.toCustomerEntity(event.getCustomer());
        customer.setId(customerId);
        when(customerRepository.findByPhoneNumbers(List.of(phoneNumber))).thenReturn(List.of(customer));

        customerEventsStoreService.storeAllEvents(new EventsGroup(List.of(event)));

        ArgumentCaptor<List<MeetingEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(meetingEntityMapper, times(1)).mapToEntity(any(MeetEvent.class));
        verify(customerRepository, times(1)).findByPhoneNumbers(List.of(phoneNumber));
        verify(genericDAO, times(1)).saveInBatch(captor.capture());
        assertEquals(customerId, captor.getValue().get(0).getCustomer().getId());
    }

    private MeetEvent createEvent(String phoneNumber) {
        Customer cust = new Customer();
        cust.setName("test");
        cust.setPhoneNumber(phoneNumber);
        MeetEvent meetEvent = new MeetEvent();
        meetEvent.setCustomer(cust);
        meetEvent.setPhoneNumber(phoneNumber);
        meetEvent.setStartTime(OffsetDateTime.now());
        return meetEvent;
    }
}