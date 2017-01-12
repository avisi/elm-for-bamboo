package nl.avisi.bamboo.plugins.elmforbamboo.format;

import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.utils.process.ExternalProcess;

import nl.avisi.bamboo.plugins.elmforbamboo.ElmFormatConfigurator;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ElmFormatCheckerTask implements TaskType {

    private final ProcessService processService;

    public ElmFormatCheckerTask(ProcessService processService) {
        this.processService = processService;
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        final String elmFormatLocation = taskContext.getConfigurationMap().get(ElmFormatConfigurator.ELM_FORMAT_LOCATION);
        final String elmFormatPaths = taskContext.getConfigurationMap().get(ElmFormatConfigurator.ELM_FORMAT_PATHS);

        TaskResultBuilder builder = TaskResultBuilder.newBuilder(taskContext);

        ExternalProcess process =
                processService.createExternalProcess(taskContext,
                        new ExternalProcessBuilder()
                                .command(Arrays.asList(elmFormatLocation,
                                        elmFormatPaths,
                                        "--validate"))
                                .workingDirectory(taskContext.getWorkingDirectory()));

        process.execute();

        return builder.checkReturnCode(process, 0).build();
    }
}
