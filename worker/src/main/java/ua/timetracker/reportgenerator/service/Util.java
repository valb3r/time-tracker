package ua.timetracker.reportgenerator.service;

import lombok.experimental.UtilityClass;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;

@UtilityClass
public class Util {

    public static StepExecution getExecution(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution();
    }
}
