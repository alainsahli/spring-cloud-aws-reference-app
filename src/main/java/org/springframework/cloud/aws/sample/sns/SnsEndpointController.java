package org.springframework.cloud.aws.sample.sns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.sample.websocket.DataWithTimestamp;
import org.springframework.cloud.aws.sample.websocket.SendingTextWebSocketHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Alain Sahli
 */
@RestController
public class SnsEndpointController {

    private static Logger LOG = LoggerFactory.getLogger(SnsEndpointController.class);

    private final SendingTextWebSocketHandler snsSendingTextWebSocketHandler;

    @Autowired
    public SnsEndpointController(@Qualifier("snsWebSocketHandler") SendingTextWebSocketHandler snsSendingTextWebSocketHandler) {
        this.snsSendingTextWebSocketHandler = snsSendingTextWebSocketHandler;
    }

    @NotificationSubscriptionMapping("/sns/receive")
    public void confirmSubscription(NotificationStatus notificationStatus) {
        notificationStatus.confirmSubscription();
    }

    @NotificationMessageMapping("/sns/receive")
    public void receiveNotification(@NotificationMessage String message, @NotificationSubject String subject) {
        LOG.debug("Received SNS message {} with subject {}", message, subject);

        try {
            this.snsSendingTextWebSocketHandler.broadcastToSessions(new DataWithTimestamp<>(new SnsNotification(subject, message)));
        } catch (IOException e) {
            LOG.error("Was not able to push the message to the client.", e);
        }
    }

}
