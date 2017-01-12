package nl.avisi.bamboo.plugins.elmforbamboo.parser;

import com.atlassian.bamboo.build.test.TestCollationService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ElmTestParserTask implements TaskType {

    private final TestCollationService testCollationService;

    public ElmTestParserTask(TestCollationService testCollationService) {
        this.testCollationService = testCollationService;
    }

    @NotNull
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        TaskResultBuilder taskResultBuilder = TaskResultBuilder.create(taskContext);

        final String testOutputFile = taskContext.getConfigurationMap().get("testOutputFile");

        File targetFile = new File(taskContext.getWorkingDirectory(), testOutputFile);
        if (targetFile.exists()) {
            testCollationService.collateTestResults(taskContext, testOutputFile, new ElmTestReportCollector());
        }
        return taskResultBuilder.checkTestFailures().build();
    }

}
