package org.brycom.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SMSContext {
    private String receiver;
    private String message;
}
