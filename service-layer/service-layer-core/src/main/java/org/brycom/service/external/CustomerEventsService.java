package org.brycom.service.external;

import org.brycom.valueobject.EventsGroup;
import org.brycom.valueobject.MeetEvent;
import org.brycom.valueobject.MeetEventState;
import org.brycom.valueobject.MeetEventsSelectionPeriod;

public interface CustomerEventsService {
    void storeAllEvents(EventsGroup meetEventsGroup);

    EventsGroup findEvents(MeetEventsSelectionPeriod selectionPeriod, MeetEventState meetEventState);

    void mergeEvent(MeetEvent meetEvent);
}
