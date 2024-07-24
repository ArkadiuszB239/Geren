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
    private String phoneNumber;
    private String sourceCalendar;

    public MeetEvent invalid() {
        this.notificationState = NotificationState.INVALID;
        this.customer = null;
        return this;
    }

    public boolean isValid() {
        return !isInvalid();
    }

    public boolean isInvalid() {
        return NotificationState.INVALID.equals(this.notificationState);
    }
}
