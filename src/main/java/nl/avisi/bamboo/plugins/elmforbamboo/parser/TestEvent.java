package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TestEvent {

    private final String event;
    private final List<String> labels;
    private final List<Failure> failures;
    private final String status;
    private final String duration;

    @JsonCreator
    public TestEvent(@NotNull @JsonProperty("event") final String event,
                     @NotNull @JsonProperty("labels") final List<String> labels,
                     @NotNull @JsonProperty("failures") final List<Failure> failures,
                     @NotNull @JsonProperty("status") final String status,
                     @NotNull @JsonProperty("duration") final String duration) {
        this.event = checkNotNull(event);
        this.labels = Lists.newArrayList(labels);
        this.failures = Lists.newArrayList(failures);
        this.status = checkNotNull(status);
        this.duration = checkNotNull(duration);
    }

    @NotNull
    public String getEvent() {
        return event;
    }

    @NotNull
    public List<String> getLabels() {
        return Lists.newArrayList(labels);
    }

    @NotNull
    public List<Failure> getFailures() {
        return Lists.newArrayList(failures);
    }

    @NotNull
    public String getStatus() {
        return status;
    }

    @NotNull
    public String getDuration() {
        return duration;
    }
}
