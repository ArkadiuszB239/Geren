package org.brycom.service.external.impl;

import lombok.extern.slf4j.Slf4j;
import org.brycom.service.external.SMSGatewayService;
import org.brycom.valueobject.SMSContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(name = "calendar-events-processing.notification-medium", havingValue = "MOCK", matchIfMissing = true)
public class SMSGatewayMock implements SMSGatewayService {
    @Override
    public void publish(SMSContext context) {
        log.info("MOCK: SMS published to: {} with content: {}", context.getReceiver(), context.getMessage());
    }
}
