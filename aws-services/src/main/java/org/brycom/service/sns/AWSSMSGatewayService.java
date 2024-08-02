package org.brycom.service.sns;

import io.awspring.cloud.sns.sms.SmsMessageAttributes;
import io.awspring.cloud.sns.sms.SmsType;
import io.awspring.cloud.sns.sms.SnsSmsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brycom.service.external.SMSGatewayService;
import org.brycom.valueobject.SMSContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "calendar-events-processing.notification-medium", havingValue = "AWS")
public class AWSSMSGatewayService implements SMSGatewayService {

    private final SnsSmsTemplate smsTemplate;

    @Override
    public void publish(SMSContext context) {
        log.info("Sending sms message through AWS SNS to {} with content {}", context.getReceiver(), context.getMessage());
        smsTemplate.send(
                context.getReceiver(),
                context.getMessage(),
                SmsMessageAttributes.builder()
                        .senderID("BarberShop")
                        .smsType(SmsType.PROMOTIONAL)
                        .build()
        );
    }
}
