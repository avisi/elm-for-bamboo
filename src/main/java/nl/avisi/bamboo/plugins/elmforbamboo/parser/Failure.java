package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class Failure {

    private final String given;
    private final String actual;

    @JsonCreator
    public Failure(@NotNull @JsonProperty("given") final String given,
                   @NotNull @JsonProperty("actual") final String actual) {
        this.given = checkNotNull(given);
        this.actual = checkNotNull(actual);
    }

    @NotNull
    public String getGiven() {
        return given;
    }

    @NotNull
    public String getActual() {
        return actual;
    }
}
