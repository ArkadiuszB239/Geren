package org.brycom.service;

import lombok.RequiredArgsConstructor;
import org.brycom.service.external.CustomerEventsService;
import org.brycom.service.external.SMSGatewayService;
import org.brycom.valueobject.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerEventsProcessingService {

    private final CustomerEventsService customerEventsService;
    private final SMSGatewayService smsGatewayService;

    public void processValidEvents() {
        EventsGroup eventsGroup = customerEventsService.findEvents(MeetEventsSelectionPeriod.DAY, MeetEventState.NEW);

        for (MeetEvent event: eventsGroup.getValidEvents()) {
            SMSContext context = createContext(event);
            smsGatewayService.publish(context);
            event.setMeetEventState(MeetEventState.PROCESSED);
            customerEventsService.mergeEvent(event);
        }
    }

    private SMSContext createContext(MeetEvent meetEvent) {
        String message = String.format(
                "Hello, we would like to inform you about your meeting today at %s:%s",
                meetEvent.getStartTime().getHour(),
                meetEvent.getStartTime().getMinute()
        );

        return new SMSContext(
                meetEvent.getPhoneNumber(),
                message
        );
    }
}