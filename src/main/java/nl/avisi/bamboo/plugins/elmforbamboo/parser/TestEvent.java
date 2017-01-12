package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TestEvent {

    private final String event;
    private final List<String> labels;
    private final List<Failure> failures;
    private final String status;
    private final String duration;

    @JsonCreator
    public TestEvent(@JsonProperty("event") String event,
                     @JsonProperty("labels") List<String> labels,
                     @JsonProperty("failures") List<Failure> failures,
                     @JsonProperty("string") String status,
                     @JsonProperty("duration") String duration) {
        this.event = event;
        this.labels = labels;
        this.failures = failures;
        this.status = status;
        this.duration = duration;
    }

    public String getEvent() {
        return event;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<Failure> getFailures() {
        return failures;
    }

    public String getStatus() {
        return status;
    }

    public String getDuration() {
        return duration;
    }
}
