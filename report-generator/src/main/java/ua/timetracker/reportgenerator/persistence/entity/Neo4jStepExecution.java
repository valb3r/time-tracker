package ua.timetracker.reportgenerator.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import ua.timetracker.reportgenerator.config.neo4jbatch.dao.CycleAvoidingMappingContext;
import ua.timetracker.reportgenerator.config.neo4jbatch.dao.converters.ExecutionContextConverter;
import ua.timetracker.reportgenerator.config.neo4jbatch.dao.converters.ExitStatusConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;
import static ua.timetracker.reportgenerator.persistence.BatchRelationshipConst.PARENT;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Neo4jStepExecution {

    public static final Neo4jStepExecution.FromBatch MAP = Mappers.getMapper(Neo4jStepExecution.FromBatch.class);

    @Id
    @GeneratedValue
    private Long id;
    
    private String stepName;
    private BatchStatus status = BatchStatus.STARTING;
    private int readCount = 0;
    private int writeCount = 0;
    private int commitCount = 0;
    private int rollbackCount = 0;
    private int readSkipCount = 0;
    private int processSkipCount = 0;
    private int writeSkipCount = 0;
    private Date startTime = new Date(System.currentTimeMillis());
    private Date endTime = null;
    private Date lastUpdated = null;

    @Convert(ExitStatusConverter.class)
    private ExitStatus exitStatus = ExitStatus.EXECUTING;

    private boolean terminateOnly;
    private int filterCount;

    @Relationship(type = PARENT, direction = OUTGOING)
    private Neo4jJobExecution jobExecution;

    @Convert(ExecutionContextConverter.class)
    private Map<String, Object> executionContext;

    @Mapper
    public interface FromBatch {

        Neo4jStepExecution map(StepExecution source);

        StepExecution map(Neo4jStepExecution source, @MappingTarget StepExecution target);

        default StepExecution map(Neo4jStepExecution source) {
            return map(
                source,
                new StepExecution(
                    source.getStepName(),
                    Neo4jJobExecution.MAP.map(source.getJobExecution(), new CycleAvoidingMappingContext()))
            );
        }

        default Map<String, Object> map(ExecutionContext value) {
            Map<String, Object> result = new HashMap<>();
            for (Map.Entry<String, Object> me : value.entrySet()) {
                result.put(me.getKey(), me.getValue());
            }

            return result;
        }

        default ExecutionContext map(Map<String, Object> value) {
            return new ExecutionContext(value);
        }
    }
}
