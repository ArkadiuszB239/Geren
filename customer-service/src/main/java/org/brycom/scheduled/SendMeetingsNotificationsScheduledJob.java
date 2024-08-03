package org.brycom.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brycom.service.CustomerEventsProcessingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "calendar-events-processing.job.enabled", havingValue = "true")
public class SendMeetingsNotificationsScheduledJob {

    private final CustomerEventsProcessingService eventsProcessingService;

    @Scheduled(cron = "${calendar-events-processing.job.cron}")
    public void run() {
        log.info("Meetings notification sending process started!");
        eventsProcessingService.processValidEvents();
        log.info("Meetings notification sending process finished!");
    }
}
