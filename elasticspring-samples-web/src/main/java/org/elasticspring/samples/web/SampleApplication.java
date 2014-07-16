package org.elasticspring.samples.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Alain Sahli
 */
@EnableAutoConfiguration
@ComponentScan
@ImportResource("classpath:org/elasticspring/samples/web/aws-stack.xml")
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

}
