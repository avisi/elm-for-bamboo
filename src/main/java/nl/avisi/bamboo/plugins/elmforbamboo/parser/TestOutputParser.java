package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.atlassian.bamboo.build.test.TestCollectionResult;
import com.atlassian.bamboo.build.test.TestCollectionResultBuilder;
import com.atlassian.bamboo.results.tests.TestResults;
import com.atlassian.bamboo.resultsummary.tests.TestCaseResultErrorImpl;
import com.atlassian.bamboo.resultsummary.tests.TestState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import org.jetbrains.annotations.NotNull;
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

    @NotNull
    public TestCollectionResult parse(@NotNull final List<String> lines) {
        final TestCollectionResultBuilder builder = new TestCollectionResultBuilder();
        final Collection<TestResults> successfulTestResults = Lists.newArrayList();
        final Collection<TestResults> failingTestResults = Lists.newArrayList();

        for (final String line : lines) {
            final Optional<TestEvent> eventOptional = getTestEvent(line);
            eventOptional.ifPresent(event -> {
                if ("testCompleted".equals(event.getEvent())) {
                    final String name = event.getLabels().stream().collect(Collectors.joining(DELIMITER));
                    final int testNameIndex = name.lastIndexOf(DELIMITER);
                    final String suiteName = name.substring(0, testNameIndex);
                    final String testName = name.substring(testNameIndex + 1);

                    //Deprecated method is used to comply with bamboo 5.11
                    final TestResults testResults = new TestResults(
                            suiteName,
                            testName,
                            String.valueOf(Long.parseLong(event.getDuration()) / 1000)
                    );

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

    @NotNull
    private Optional<TestEvent> getTestEvent(@NotNull final String input) {
        try {
            return Optional.of(objectMapper.readValue(input, TestEvent.class));
        } catch (IOException e) {
            LOGGER.warn("Could not parse: {}", input);
            return Optional.empty();
        }
    }
}
