package org.brycom.valueobject;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class MeetEvent {
    private Customer customer;
    private OffsetDateTime startTime;
    private NotificationState notificationState = NotificationState.NEW;

    public MeetEvent invalid() {
        this.notificationState = NotificationState.INVALID;
        return this;
    }
}
