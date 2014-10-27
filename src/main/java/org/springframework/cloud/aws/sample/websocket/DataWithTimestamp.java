package org.springframework.cloud.aws.sample.websocket;

import java.util.Date;

/**
 * @author Alain Sahli
 */
public class DataWithTimestamp<T> {

    private final T data;

    private final String timestamp;

    public DataWithTimestamp(T data) {
        this(data, String.valueOf(new Date().getTime()));
    }

    public DataWithTimestamp(T data, String timestamp) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public String getTimestamp() {
        return this.timestamp;
    }
}
