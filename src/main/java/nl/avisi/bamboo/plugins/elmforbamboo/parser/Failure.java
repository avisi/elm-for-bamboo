package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Failure {

    private final String given;
    private final String actual;

    @JsonCreator
    public Failure(@JsonProperty("given") String given, @JsonProperty("actual") String actual) {
        this.given = given;
        this.actual = actual;
    }

    public String getGiven() {
        return given;
    }

    public String getActual() {
        return actual;
    }
}
