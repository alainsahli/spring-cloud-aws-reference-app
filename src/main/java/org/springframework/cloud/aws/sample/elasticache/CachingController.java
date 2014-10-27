package org.springframework.cloud.aws.sample.elasticache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Agim Emruli
 */
@RestController
public class CachingController {

    private final ExpensiveService expensiveService;

    @Autowired
    public CachingController(ExpensiveService expensiveService) {
        this.expensiveService = expensiveService;
    }

    @RequestMapping("/cachedService")
    public String getCachedValue() {
        return expensiveService.calculateExpensiveValue();
    }
}
