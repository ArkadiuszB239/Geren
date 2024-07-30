package org.brycom.service.external;

import org.brycom.valueobject.SMSContext;

public interface SMSGatewayService {
    void publish(SMSContext context);
}
