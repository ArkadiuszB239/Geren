package org.brycom.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brycom.service.CustomerEventsCollectingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "calendar-events-collecting.job.enabled", havingValue = "true")
public class StoreMeetingsScheduledJob {

    private final CustomerEventsCollectingService customerEventsCollectingService;

    @Scheduled(cron = "${calendar-events-collecting.job.cron}")
    public void run() {
        log.info("Store daily meeting scheduled job started!");
        customerEventsCollectingService.collectAndStoreCustomerEvents();
        log.info("Store daily meeting scheduled job finished!");
    }
}
