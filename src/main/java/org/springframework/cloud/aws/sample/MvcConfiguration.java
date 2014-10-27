package org.springframework.cloud.aws.sample;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.endpoint.NotificationMessageHandlerMethodArgumentResolver;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatusHandlerMethodArgumentResolver;
import org.springframework.cloud.aws.messaging.endpoint.NotificationSubjectHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author Alain Sahli
 */
@Component
public class MvcConfiguration {

    @Autowired
    public MvcConfiguration(RequestMappingHandlerAdapter requestMappingHandlerAdapter, AmazonSNS amazonSNS) {
        requestMappingHandlerAdapter.getCustomArgumentResolvers().add(new NotificationMessageHandlerMethodArgumentResolver());
        requestMappingHandlerAdapter.getCustomArgumentResolvers().add(new NotificationSubjectHandlerMethodArgumentResolver());
        requestMappingHandlerAdapter.getCustomArgumentResolvers().add(new NotificationStatusHandlerMethodArgumentResolver(amazonSNS));
    }
}
