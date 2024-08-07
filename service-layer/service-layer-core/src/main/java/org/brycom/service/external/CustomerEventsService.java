package org.brycom.service.external;

import org.brycom.valueobject.EventsGroup;
import org.brycom.valueobject.MeetEvent;
import org.brycom.valueobject.MeetEventState;
import org.brycom.valueobject.SelectionPeriod;

public interface CustomerEventsService {
    void storeAllEvents(EventsGroup meetEventsGroup);

    void mergeEvent(MeetEvent meetEvent);

    EventsGroup findEvents(SelectionPeriod selectionPeriod, MeetEventState meetEventState);

    EventsGroup findEvents(SelectionPeriod selectionPeriod);
}
