package org.brycom.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brycom.service.CustomerEventsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduled.jobs.daily-meetings.enabled", havingValue = "true")
public class StoreMeetingsScheduledJob {

    private final CustomerEventsService customerEventsService;

    @Scheduled(cron = "${scheduled.jobs.daily-meetings.cron}")
    public void run() {
        log.info("Store daily meeting scheduled job started!");
        customerEventsService.collectAndStoreCustomerEvents();
        log.info("Store daily meeting scheduled job finished!");
    }
}