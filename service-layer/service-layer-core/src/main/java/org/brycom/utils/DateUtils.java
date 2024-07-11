package org.brycom.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class DateUtils {
    public LocalDateTime getDayStart() {
        return LocalDate.now().atStartOfDay();
    }

    public LocalDateTime getDayEnd() {
        return LocalDate.now().plusDays(1).atStartOfDay();
    }
}
