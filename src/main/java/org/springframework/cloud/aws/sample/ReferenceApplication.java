package org.springframework.cloud.aws.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.Collections;

/**
 * @author Alain Sahli
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableWebSocket
@EnableCaching
@ImportResource("classpath:org/springframework/cloud/aws/sample/aws-stack.xml")
public class ReferenceApplication implements EmbeddedServletContainerCustomizer {

    public static void main(String[] args) {
        SpringApplication.run(ReferenceApplication.class, args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("html", "text/html;charset=utf-8");
        container.setMimeMappings(mappings);
    }

    @Bean
    @Profile("local")
    public CacheManager createSimpleCacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Collections.singleton(new ConcurrentMapCache("CacheCluster")));
        return simpleCacheManager;
    }
}