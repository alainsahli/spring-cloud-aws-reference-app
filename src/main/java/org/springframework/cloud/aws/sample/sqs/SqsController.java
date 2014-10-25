package org.springframework.cloud.aws.sample.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alain Sahli
 */
@RestController
@RequestMapping("/sqs")
public class SqsController {

    private static final Logger LOG = LoggerFactory.getLogger(SqsController.class);

    public static final String QUEUE_NAME = "MessageProcessingQueue";

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SqsController(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @RequestMapping(value = "/message-processing-queue", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void sendMessageToMessageProcessingQueue(@RequestBody MessageToProcess message) {
        LOG.debug("Going to send message {} over SQS", message);

        this.queueMessagingTemplate.convertAndSend(QUEUE_NAME, message);
    }

}
