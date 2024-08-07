package org.brycom.valueobject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.brycom.utils.DateUtils;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public enum SelectionPeriod {
    DAY(DateUtils.getDayStart(), DateUtils.getDayEnd()),
    WEEK(DateUtils.getWeekStart(), DateUtils.getDayEnd()),
    MONTH(DateUtils.getMonthStart(), DateUtils.getDayEnd());

    private final LocalDateTime periodStart, periodEnd;
}
