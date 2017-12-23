package nl.avisi.bamboo.plugins.elmforbamboo.format;

import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.utils.process.ExternalProcess;
import com.google.common.collect.Lists;

import nl.avisi.bamboo.plugins.elmforbamboo.ElmFormatConfigurator;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElmFormatCheckerTask implements TaskType {

    private final ProcessService processService;

    public ElmFormatCheckerTask(@NotNull final ProcessService processService) {
        this.processService = checkNotNull(processService);
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull final TaskContext taskContext) {
        final String elmFormatLocation = taskContext.getConfigurationMap().get(ElmFormatConfigurator.ELM_FORMAT_LOCATION);
        final String elmFormatPaths = taskContext.getConfigurationMap().get(ElmFormatConfigurator.ELM_FORMAT_PATHS);
        final String[] paths = elmFormatPaths.split(",");

        final List<String> command = Lists.newArrayList();
        command.add(elmFormatLocation);
        command.addAll(Arrays.asList(paths));
        command.add("--validate");

        final TaskResultBuilder builder = TaskResultBuilder.newBuilder(taskContext);

        final ExternalProcess process =
                processService.createExternalProcess(taskContext,
                        new ExternalProcessBuilder()
                                .command(command)
                                .workingDirectory(taskContext.getWorkingDirectory()));

        process.execute();

        return builder.checkReturnCode(process, 0).build();
    }
}
