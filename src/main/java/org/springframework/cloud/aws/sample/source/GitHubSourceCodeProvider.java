/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.aws.sample.source;

import org.apache.commons.codec.binary.Base64;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Alain Sahli
 */
@Service
public class GitHubSourceCodeProvider implements SourceCodeProvider {

    private static final String BASE_URL = "https://api.github.com/repos/alainsahli/spring-cloud-aws-reference-app/contents/";

    private final RestTemplate restTemplate;

    public GitHubSourceCodeProvider() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    @Cacheable(value = "GitHubSourceCode")
    public SourceFile getSourceFileContent(String path) {
        @SuppressWarnings("unchecked")
        Map<String, String> result = this.restTemplate.getForObject(BASE_URL + path, Map.class);
        return new SourceFile(new String(Base64.decodeBase64(result.get("content"))), result.get("html_url"));
    }
}
