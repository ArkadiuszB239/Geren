package org.brycom.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.brycom.entities.MeetingEntity;
import org.brycom.mapper.MeetingEntityMapper;
import org.brycom.repository.MeetingRepository;
import org.brycom.service.external.CustomerEventsService;
import org.brycom.valueobject.EventsGroup;
import org.brycom.valueobject.MeetEvent;
import org.brycom.valueobject.MeetEventState;
import org.brycom.valueobject.SelectionPeriod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerEventsServiceImpl implements CustomerEventsService {

    private final MeetingRepository meetingRepository;
    private final CustomerEventsStoreService storeService;
    private final MeetingEntityMapper meetingEntityMapper;

    @Override
    @Transactional
    public void storeAllEvents(EventsGroup meetEventsGroup) {
        storeService.storeEvents(meetEventsGroup);
    }

    @Override
    public EventsGroup findEvents(SelectionPeriod selectionPeriod, MeetEventState meetEventState) {
        List<MeetEvent> meetEvents = meetingRepository.findBySelectionPeriodAndState(selectionPeriod, meetEventState)
                .stream()
                .map(meetingEntityMapper::mapToEvent)
                .toList();

        return new EventsGroup(meetEvents);
    }

    @Override
    public EventsGroup findEvents(SelectionPeriod selectionPeriod) {
        List<MeetEvent> meetEvents = meetingRepository.findBySeleltionPeriod(selectionPeriod)
                .stream()
                .map(meetingEntityMapper::mapToEvent)
                .toList();

        return new EventsGroup(meetEvents);
    }

    @Override
    @Transactional
    public void mergeEvent(MeetEvent meetEvent) {
        //Only state now, but can be extended with additional fields
        MeetingEntity meetingEntity = meetingRepository.findById(meetEvent.getMeetingId());
        meetingEntity.setMeetEventState(meetEvent.getMeetEventState());
    }
}
