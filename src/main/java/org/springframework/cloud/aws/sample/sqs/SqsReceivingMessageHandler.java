package org.springframework.cloud.aws.sample.sqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author Alain Sahli
 */
public class SqsReceivingMessageHandler extends TextWebSocketHandler {

    private final Logger LOG = LoggerFactory.getLogger(SqsReceivingMessageHandler.class);

    private static final String QUEUE_NAME = "MessageProcessingQueue";

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public SqsReceivingMessageHandler() {
        LOG.debug("SqsReceivingMessageHandler was instantiated hashCode: {}", this.hashCode());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.sessions.add(session);
    }

    @MessageMapping(QUEUE_NAME)
    private void receiveMessage(MessageToProcess message, @Header("ApproximateFirstReceiveTimestamp") String approximateFirstReceiveTimestamp) {
        try {
            sendMessageToClient(new MessageWithReceiveTimestamp(message, approximateFirstReceiveTimestamp));
        } catch (IOException e) {
            LOG.error("Was not able to push the message to the client.", e);
        }
    }

    private void sendMessageToClient(MessageWithReceiveTimestamp message) throws IOException {
        String payload = this.jsonMapper.writeValueAsString(message);
        sendTestMessageToAllSessions(payload);
    }

    private void sendTestMessageToAllSessions(String payload) throws IOException {
        this.sessions.removeAll(this.sessions.stream().filter(s -> !s.isOpen()).collect(Collectors.toList()));

        for (WebSocketSession session : this.sessions) {
            session.sendMessage(new TextMessage(payload));
        }
    }

    private static class MessageWithReceiveTimestamp {

        private final MessageToProcess messageToProcess;

        private final String receivedTimestamp;


        private MessageWithReceiveTimestamp(MessageToProcess messageToProcess, String receivedTimestamp) {
            this.messageToProcess = messageToProcess;
            this.receivedTimestamp = receivedTimestamp;
        }

        public MessageToProcess getMessageToProcess() {
            return this.messageToProcess;
        }

        public String getReceivedTimestamp() {
            return this.receivedTimestamp;
        }
    }
}
