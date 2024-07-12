package org.brycom.valueobject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class EventsGroup {
    private final List<MeetEvent> meetEvents;

    public List<MeetEvent> getValidEvents() {
        return meetEvents.stream()
                .filter(MeetEvent::isValid)
                .toList();
    }

    public List<MeetEvent> getInvalidEvents() {
        return meetEvents.stream()
                .filter(MeetEvent::isInvalid)
                .toList();
    }
}
