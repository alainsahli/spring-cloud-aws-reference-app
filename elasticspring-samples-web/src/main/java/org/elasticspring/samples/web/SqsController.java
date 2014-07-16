package org.elasticspring.samples.web;

import org.elasticspring.messaging.core.QueueMessagingTemplate;
import org.elasticspring.samples.web.model.MessageToProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Alain Sahli
 */
@RestController
@RequestMapping("/sqs")
public class SqsController {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SqsController(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @RequestMapping(value = "/message-processing-queue", method = RequestMethod.POST)
    public void sendMessageToMessageProcessingQueue(@RequestBody @Valid MessageToProcess message) {
        this.queueMessagingTemplate.convertAndSend("MessageProcessingQueue", message);
    }

}
