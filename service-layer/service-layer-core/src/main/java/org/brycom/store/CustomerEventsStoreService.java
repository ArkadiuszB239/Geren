package org.brycom.store;

import org.brycom.valueobject.EventsGroup;

public interface CustomerEventsStoreService {
    void storeAllEvents(EventsGroup meetEventsGroup);
}
