package org.springframework.cloud.aws.sample.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Alain Sahli
 */
@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sqsReceivingMessageHandler(), "/sqs-messages").withSockJS();
        registry.addHandler(snsReceivingMessageHandler(), "/sns-messages").withSockJS();
    }

    @Bean(name = "sqsWebSocketHandler")
    public SendingTextWebSocketHandler sqsReceivingMessageHandler() {
        return new SendingTextWebSocketHandler();
    }

    @Bean(name = "snsWebSocketHandler")
    public SendingTextWebSocketHandler snsReceivingMessageHandler() {
        return new SendingTextWebSocketHandler();
    }
}
