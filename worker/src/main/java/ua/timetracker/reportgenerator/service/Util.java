package ua.timetracker.reportgenerator.service;

import lombok.experimental.UtilityClass;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;

@UtilityClass
public class Util {

    public static StepExecution getExecution(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFromContext(StepExecution exec, String paramName, T defaultValueIfNull) {
        Object value = exec.getJobExecution().getExecutionContext().get(paramName);

        if (null == value) {
            return defaultValueIfNull;
        }

        return (T) value;
    }
}
