package org.springframework.cloud.aws.sample.source;

import org.apache.commons.codec.binary.Base64;
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
    public SourceFile getSourceFileContent(String path) {
        @SuppressWarnings("unchecked")
        Map<String, String> result = this.restTemplate.getForObject(BASE_URL + path, Map.class);
        return new SourceFile(new String(Base64.decodeBase64(result.get("content"))), result.get("html_url"));
    }
}
