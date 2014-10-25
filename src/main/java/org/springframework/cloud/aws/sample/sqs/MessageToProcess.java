package org.springframework.cloud.aws.sample.sqs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alain Sahli
 */
public class MessageToProcess {

    private final String message;

    private final int priority;

    @JsonCreator
    public MessageToProcess(@JsonProperty("message") String message, @JsonProperty("priority") int priority) {
        this.message = message;
        this.priority = priority;
    }

    public String getMessage() {
        return this.message;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return "MessageToProcess{" + "message='" + message + '\'' + ", priority=" + priority + '}';
    }
}
