package org.springframework.cloud.aws.sample.ec2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Agim Emruli
 */
@Component
public class ApplicationInfoBean {

    @Value("${ami-id}")
    private String amiId;

    @Value("${hostname}")
    private String hostname;

    @Value("${instance-type}")
    private String instanceType;

    @Value("${services/domain}")
    private String serviceDomain;


    public String getAmiId() {
        return this.amiId;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getInstanceType() {
        return this.instanceType;
    }

    public String getServiceDomain() {
        return this.serviceDomain;
    }
}
