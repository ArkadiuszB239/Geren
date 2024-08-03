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
@ConditionalOnProperty(name = "scheduled.jobs.meetings-notification.enabled", havingValue = "true")
public class SendMeetingsNotificationsScheduledJob {

    private final CustomerEventsProcessingService eventsProcessingService;

    @Scheduled(cron = "${scheduled.jobs.meetings-notification.cron}")
    public void run() {
        log.info("Meetings notification sending process started!");
        eventsProcessingService.processValidEvents();
        log.info("Meetings notification sending process finished!");
    }
}
