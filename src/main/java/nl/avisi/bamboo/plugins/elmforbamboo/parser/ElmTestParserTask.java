package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.atlassian.bamboo.build.test.TestCollationService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElmTestParserTask implements TaskType {

    private final TestCollationService testCollationService;

    public ElmTestParserTask(@NotNull final TestCollationService testCollationService) {
        this.testCollationService = checkNotNull(testCollationService);
    }

    @NotNull
    public TaskResult execute(@NotNull final TaskContext taskContext) {
        final TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);

        final String testOutputFile = taskContext.getConfigurationMap().get("testOutputFile");

        final File targetFile = new File(taskContext.getWorkingDirectory(), testOutputFile);
        if (targetFile.exists()) {
            testCollationService.collateTestResults(taskContext, testOutputFile, new ElmTestReportCollector());
        }

        return taskResultBuilder.checkTestFailures().build();
    }

}
