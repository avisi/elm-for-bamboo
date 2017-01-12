package nl.avisi.bamboo.plugins.elmtest.parser;

import com.atlassian.bamboo.build.test.TestCollationService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElmTestParserTask implements TaskType {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElmTestParserTask.class);
    private final TestCollationService testCollationService;

    public ElmTestParserTask(TestCollationService testCollationService) {
        this.testCollationService = testCollationService;
    }

    @NotNull
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        TaskResultBuilder taskResultBuilder = TaskResultBuilder.create(taskContext);

        final String testOutputFile = taskContext.getConfigurationMap().get("testOutputFile");


        testCollationService.collateTestResults(taskContext, testOutputFile, new ElmTestReportCollector());

        return taskResultBuilder.checkTestFailures().build();
    }

}
