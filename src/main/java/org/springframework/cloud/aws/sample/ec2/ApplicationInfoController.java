package org.springframework.cloud.aws.sample.ec2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Agim Emruli
 */
@RestController
public class ApplicationInfoController {

    private final ApplicationInfoBean infoBean;

    @Autowired
    public ApplicationInfoController(ApplicationInfoBean infoBean) {
        this.infoBean = infoBean;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApplicationInfoBean info() {
        return this.infoBean;
    }
}
