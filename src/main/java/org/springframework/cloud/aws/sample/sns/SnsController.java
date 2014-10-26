package org.springframework.cloud.aws.sample.sns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alain Sahli
 */
@RestController
@RequestMapping("/sns")
public class SnsController {

    private static Logger LOG = LoggerFactory.getLogger(SnsController.class);

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Autowired
    public SnsController(NotificationMessagingTemplate notificationMessagingTemplate) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void sendNotification(@RequestBody SnsNotification notification) {
        LOG.debug("Going to send notification {}", notification);

        this.notificationMessagingTemplate.sendNotification("", notification.getMessage(), notification.getSubject());
    }

    @NotificationMessageMapping("/receive")
    public void receiveNotification(@NotificationMessage String message, @NotificationSubject String subject) {

    }

}
