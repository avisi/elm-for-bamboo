package nl.avisi.bamboo.plugins.elmforbamboo.runner;

import com.atlassian.bamboo.process.BambooProcessHandler;
import com.atlassian.bamboo.process.ExternalProcessViaBatchBuilder;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.utils.process.ExternalProcess;
import com.atlassian.utils.process.StringOutputHandler;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ElmTestRunnerTask implements TaskType {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElmTestRunnerTask.class);

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        final String testFilePattern = taskContext.getConfigurationMap().get("testOutputFile");

        TaskResultBuilder builder = TaskResultBuilder.newBuilder(taskContext);

        StringOutputHandler outputHandler = new StringOutputHandler();
        ExternalProcess process =
                new ExternalProcessViaBatchBuilder()
                        .command(Arrays.asList("./node_modules/.bin/elm-test",
                                "--compiler=" + taskContext.getWorkingDirectory().toString() + "/node_modules/.bin/elm-make",
                                "--report=json"),
                                taskContext.getWorkingDirectory())
                        .handler(new BambooProcessHandler(outputHandler, outputHandler))
                        .build();

        process.execute();

        final String outputFile = taskContext.getWorkingDirectory() + "/" + testFilePattern;
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.append(outputHandler.getOutput());
        } catch (IOException e) {
            LOGGER.error("Could not write file {}", outputFile, e);
            taskContext.getBuildLogger().addBuildLogEntry("Could not write test results file '" + outputFile + "'");
            builder.failed().build();
        }

        return builder.checkReturnCode(process, 0).build();
    }
}
