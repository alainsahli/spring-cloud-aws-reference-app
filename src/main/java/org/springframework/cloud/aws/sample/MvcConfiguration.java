package org.springframework.cloud.aws.sample;

import org.springframework.cloud.aws.messaging.endpoint.NotificationMessageHandlerMethodArgumentResolver;
import org.springframework.cloud.aws.messaging.endpoint.NotificationSubjectHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author Alain Sahli
 */
// @Configuration
// @EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new NotificationMessageHandlerMethodArgumentResolver());
        argumentResolvers.add(new NotificationSubjectHandlerMethodArgumentResolver());
    }
}
