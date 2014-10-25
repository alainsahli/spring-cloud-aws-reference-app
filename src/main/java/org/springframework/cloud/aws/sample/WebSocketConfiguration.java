package org.springframework.cloud.aws.sample;

import org.springframework.cloud.aws.sample.sqs.SqsReceivingMessageHandler;
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
    }

    @Bean
    public SqsReceivingMessageHandler sqsReceivingMessageHandler() {
        return new SqsReceivingMessageHandler();
    }
}
