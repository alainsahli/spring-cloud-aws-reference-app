package org.springframework.cloud.aws.sample;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.cloud.aws.core.config.AmazonWebserviceClientConfigurationUtils;
import org.springframework.cloud.aws.messaging.endpoint.NotificationMessageHandlerMethodArgumentResolver;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatusHandlerMethodArgumentResolver;
import org.springframework.cloud.aws.messaging.endpoint.NotificationSubjectHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Alain Sahli
 */
@Component
public class MvcConfiguration implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        List<BeanDefinition> methodArgumentResolvers = new ManagedList<>(3);

        BeanDefinitionBuilder notificationMessageHandler = BeanDefinitionBuilder.rootBeanDefinition(NotificationMessageHandlerMethodArgumentResolver.class);
        methodArgumentResolvers.add(notificationMessageHandler.getBeanDefinition());

        BeanDefinitionBuilder notificationSubjectHandler = BeanDefinitionBuilder.rootBeanDefinition(NotificationSubjectHandlerMethodArgumentResolver.class);
        methodArgumentResolvers.add(notificationSubjectHandler.getBeanDefinition());

        BeanDefinitionBuilder notificationStatusBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(NotificationStatusHandlerMethodArgumentResolver.class);
        notificationStatusBeanDefinition.addConstructorArgReference(AmazonWebserviceClientConfigurationUtils.getBeanName(AmazonSNS.class.getName()));
        methodArgumentResolvers.add(notificationStatusBeanDefinition.getBeanDefinition());

        BeanDefinition requestMappingHandlerAdapter = beanFactory.getBeanDefinition("requestMappingHandlerAdapter");

        requestMappingHandlerAdapter.getPropertyValues().addPropertyValue("customArgumentResolvers", methodArgumentResolvers);
    }
}
