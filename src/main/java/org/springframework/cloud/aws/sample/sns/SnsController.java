package org.springframework.cloud.aws.sample.sns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.sample.websocket.DataWithTimestamp;
import org.springframework.cloud.aws.sample.websocket.SendingTextWebSocketHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Alain Sahli
 */
@RestController
public class SnsController {

    private static Logger LOG = LoggerFactory.getLogger(SnsController.class);

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    private final SendingTextWebSocketHandler snsSendingTextWebSocketHandler;

    @Autowired
    public SnsController(NotificationMessagingTemplate notificationMessagingTemplate,
                         @Qualifier("snsWebSocketHandler") SendingTextWebSocketHandler snsSendingTextWebSocketHandler) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
        this.snsSendingTextWebSocketHandler = snsSendingTextWebSocketHandler;
    }

    @RequestMapping(value = "/sns/send", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void sendNotification(@RequestBody SnsNotification notification) {
        LOG.debug("Going to send notification {}", notification);

        this.notificationMessagingTemplate.sendNotification("SnsTopic", notification.getMessage(), notification.getSubject());
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
