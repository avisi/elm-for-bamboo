package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.atlassian.bamboo.build.test.TestCollectionResult;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestOutputParserTest {

    private TestOutputParser testOutputParser;

    @Before
    public void before() {
        testOutputParser = new TestOutputParser();
    }

    @Test
    public void parseSuccessfulOutput() throws IOException {
        final InputStream resource = getClass().getResourceAsStream("/success.json");
        final List<String> lines = IOUtils.readLines(resource);

        final TestCollectionResult result = testOutputParser.parse(lines);
        assertThat(result.getSuccessfulTestResults().size(), is(3));
        assertThat(result.getSkippedTestResults().size(), is(0));
        assertThat(result.getFailedTestResults().size(), is(0));
    }

    @Test
    public void parseFailedOutput() throws IOException {
        final InputStream resource = getClass().getResourceAsStream("/failed.json");
        final List<String> lines = IOUtils.readLines(resource);

        final TestCollectionResult result = testOutputParser.parse(lines);
        assertThat(result.getSuccessfulTestResults().size(), is(1));
        assertThat(result.getSkippedTestResults().size(), is(0));
        assertThat(result.getFailedTestResults().size(), is(1));
    }

}
