package org.brycom.store;

import org.brycom.valueobject.MeetEvent;

import java.util.List;

public interface CustomerEventsStoreService {
    void storeAllEvents(List<MeetEvent> meetEvents);
}
