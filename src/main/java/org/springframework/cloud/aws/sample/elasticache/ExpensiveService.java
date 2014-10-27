package org.springframework.cloud.aws.sample.elasticache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Agim Emruli
 */
@Service
public class ExpensiveService {

    @Cacheable("CacheCluster")
    public String calculateExpensiveValue(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return UUID.randomUUID().toString();
    }
}