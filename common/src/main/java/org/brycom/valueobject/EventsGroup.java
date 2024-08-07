package org.brycom.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EventsGroup {
    private List<MeetEvent> meetEvents;

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

    public EventsGroup subtract(EventsGroup group) {
        this.meetEvents = this.meetEvents.stream()
                .filter(event -> !group.getMeetEvents().contains(event))
                .toList();
        return this;
    }
}
