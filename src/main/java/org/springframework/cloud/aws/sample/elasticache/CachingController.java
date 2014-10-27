package org.springframework.cloud.aws.sample.elasticache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Agim Emruli
 */
@Controller
public class CachingController {

    private final ExpensiveService expensiveService;

    @Autowired
    public CachingController(ExpensiveService expensiveService) {
        this.expensiveService = expensiveService;
    }

    @RequestMapping(value = "/cachedService", produces = "text/plain")
    public ResponseEntity getCachedValue(@RequestParam String key) {
        String responseValue = this.expensiveService.calculateExpensiveValue(key);
        return new ResponseEntity<>(responseValue, HttpStatus.OK);
    }
}
