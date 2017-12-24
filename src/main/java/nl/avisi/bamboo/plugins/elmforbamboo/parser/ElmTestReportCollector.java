package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.atlassian.bamboo.build.test.TestCollectionResult;
import com.atlassian.bamboo.build.test.TestReportCollector;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Set;

public class ElmTestReportCollector implements TestReportCollector {

    @NotNull
    public TestCollectionResult collect(@NotNull final File file) throws Exception {
        final List<String> lines = Files.readLines(file, Charsets.UTF_8);

        final TestOutputParser testOutputParser = new TestOutputParser();
        return testOutputParser.parse(lines);
    }

    @NotNull
    public Set<String> getSupportedFileExtensions() {
        return Sets.newHashSet("json");
    }
}
