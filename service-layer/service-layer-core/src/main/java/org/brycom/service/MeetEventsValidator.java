package org.brycom.service;


import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import lombok.experimental.UtilityClass;
import org.brycom.valueobject.MeetEvent;

@UtilityClass
public class MeetEventsValidator {
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public boolean isValid(MeetEvent meetEvent) {
        try {
            PhoneNumber phonenumber = phoneNumberUtil.parse(meetEvent.getCustomer().getPhoneNumber(), "PL");
            return phoneNumberUtil.isValidNumber(phonenumber);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
