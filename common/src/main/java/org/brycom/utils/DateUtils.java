package org.brycom.utils;

import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@UtilityClass
public class DateUtils {

    public DateTimeFormatter hourAndMinutedFormatter(){
        return DateTimeFormatter.ofPattern("HH:mm");
    }

    public LocalDateTime getDayStart() {
        return LocalDate.now().atStartOfDay();
    }

    public LocalDateTime getDayEnd() {
        return LocalDate.now().plusDays(1).atStartOfDay();
    }

    public LocalDateTime getWeekStart() {
        return LocalDate.now().atStartOfDay().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public LocalDateTime getMonthStart() {
        return LocalDate.now().atStartOfDay().with(TemporalAdjusters.firstDayOfMonth());

    }
}
