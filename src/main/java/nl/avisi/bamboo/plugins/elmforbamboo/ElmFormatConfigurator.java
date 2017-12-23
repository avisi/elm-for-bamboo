package nl.avisi.bamboo.plugins.elmforbamboo;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.utils.i18n.DefaultI18nBean;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ElmFormatConfigurator extends AbstractTaskConfigurator implements TaskConfigurator {

    public static final String ELM_FORMAT_LOCATION = "elmFormatLocation";
    public static final String ELM_FORMAT_PATHS = "elmFormatPaths";

    private DefaultI18nBean textProvider;

    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
        context.put(ELM_FORMAT_LOCATION, "elm-format");
        context.put(ELM_FORMAT_PATHS, "src");
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        context.put(ELM_FORMAT_LOCATION, taskDefinition.getConfiguration().get(ELM_FORMAT_LOCATION));
        context.put(ELM_FORMAT_PATHS, taskDefinition.getConfiguration().get(ELM_FORMAT_PATHS));
    }

    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);

        final String formatLocation = params.getString(ELM_FORMAT_LOCATION);
        if (StringUtils.isEmpty(formatLocation)) {
            errorCollection.addError(ELM_FORMAT_LOCATION, textProvider.getText("config.elmFormatLocation.error"));
        }

        final String paths = params.getString(ELM_FORMAT_PATHS);
        if (StringUtils.isEmpty(paths)) {
            errorCollection.addError(ELM_FORMAT_PATHS, textProvider.getText("config.elmFormatPaths.error"));
        }
    }

    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition taskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, taskDefinition);
        config.put(ELM_FORMAT_LOCATION, params.getString(ELM_FORMAT_LOCATION));
        config.put(ELM_FORMAT_PATHS, params.getString(ELM_FORMAT_PATHS));
        return config;
    }

}
