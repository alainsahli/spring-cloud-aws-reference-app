package org.elasticspring.samples.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
}
