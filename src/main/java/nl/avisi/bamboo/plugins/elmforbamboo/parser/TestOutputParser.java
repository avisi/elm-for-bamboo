package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.atlassian.bamboo.build.test.TestCollectionResult;
import com.atlassian.bamboo.build.test.TestCollectionResultBuilder;
import com.atlassian.bamboo.results.tests.TestResults;
import com.atlassian.bamboo.resultsummary.tests.TestCaseResultErrorImpl;
import com.atlassian.bamboo.resultsummary.tests.TestState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestOutputParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElmTestReportCollector.class);
    private static final String DELIMITER = "/";

    private final ObjectMapper objectMapper;

    public TestOutputParser() {
        objectMapper = new ObjectMapper();
    }

    public TestCollectionResult parse(List<String> lines) {
        TestCollectionResultBuilder builder = new TestCollectionResultBuilder();
        Collection<TestResults> successfulTestResults = Lists.newArrayList();
        Collection<TestResults> failingTestResults = Lists.newArrayList();

        for (String line : lines) {
            Optional<TestEvent> eventOptional = getTestEvent(line);
            eventOptional.ifPresent(event -> {
                if ("testCompleted".equals(event.getEvent())) {
                    final String name = event.getLabels().stream().collect(Collectors.joining(DELIMITER));
                    final int testNameIndex = name.lastIndexOf(DELIMITER);
                    final String suiteName = name.substring(0, testNameIndex);
                    final String testName = name.substring(testNameIndex + 1);

                    TestResults testResults = new TestResults(suiteName, testName, Long.parseLong(event.getDuration()));
                    if ("pass".equals(event.getStatus())) {
                        testResults.setState(TestState.SUCCESS);
                        successfulTestResults.add(testResults);
                    } else {
                        testResults.setState(TestState.FAILED);
                        final String errors = event.getFailures().stream()
                                .map(failure -> failure.getActual() + failure.getGiven())
                                .collect(Collectors.joining("\n-------------\n"));
                        testResults.addError(new TestCaseResultErrorImpl(errors));
                        failingTestResults.add(testResults);
                    }
                }
            });
        }

        return builder
                .addSuccessfulTestResults(successfulTestResults)
                .addFailedTestResults(failingTestResults)
                .build();
    }

    private Optional<TestEvent> getTestEvent(String input) {
        try {
            return Optional.of(objectMapper.readValue(input, TestEvent.class));
        } catch (IOException e) {
            LOGGER.warn("Could not parse: {}", input);
            return Optional.empty();
        }
    }
}
