package org.brycom.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Setter
@Getter
@Validated
@Configuration
@ConfigurationProperties(prefix = "calendar-events-collecting")
public class CalendarEventsCollectingConfig {
    @NotNull
    private List<String> calendars;
}
