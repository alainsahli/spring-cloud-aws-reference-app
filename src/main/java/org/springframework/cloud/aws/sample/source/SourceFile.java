package org.springframework.cloud.aws.sample.source;

/**
 * @author Alain Sahli
 */
public class SourceFile {

    private final String content;

    private final String url;

    public SourceFile(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String getContent() {
        return this.content;
    }

    public String getUrl() {
        return this.url;
    }
}
