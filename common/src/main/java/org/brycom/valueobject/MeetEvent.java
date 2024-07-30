package org.brycom.valueobject;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.OffsetDateTime;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;

@Getter
@Setter
public class MeetEvent {
    private Long meetingId;
    private Customer customer;
    private OffsetDateTime startTime;
    private MeetEventState meetEventState = MeetEventState.NEW;
    private String phoneNumber;
    private String sourceCalendar;

    public MeetEvent invalid() {
        this.meetEventState = MeetEventState.INVALID;
        this.customer = null;
        return this;
    }

    public boolean isValid() {
        return !isInvalid();
    }

    public boolean isInvalid() {
        return MeetEventState.INVALID.equals(this.meetEventState);
    }

    @SneakyThrows
    public MeetEvent valid() {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phoneNumberWrapper = phoneNumberUtil.parse(this.phoneNumber, "PL");
        this.phoneNumber = phoneNumberUtil.format(phoneNumberWrapper, E164);
        this.customer.setPhoneNumber(this.phoneNumber);
        return this;
    }
}
