package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.atlassian.bamboo.build.test.TestCollectionResult;
import com.atlassian.bamboo.build.test.TestReportCollector;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

public class ElmTestReportCollector implements TestReportCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElmTestReportCollector.class);
    private static final String DELIMITER = "/";

    @NotNull
    public TestCollectionResult collect(@NotNull File file) throws Exception {
        List<String> lines = Files.readLines(file, Charset.forName("UTF-8"));

        TestOutputParser testOutputParser = new TestOutputParser();
        return testOutputParser.parse(lines);
    }

    @NotNull
    public Set<String> getSupportedFileExtensions() {
        return Sets.newHashSet("json");
    }

}
