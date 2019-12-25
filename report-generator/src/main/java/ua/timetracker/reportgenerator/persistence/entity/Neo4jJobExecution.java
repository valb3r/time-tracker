package ua.timetracker.reportgenerator.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.item.ExecutionContext;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.INCOMING;
import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;
import static ua.timetracker.reportgenerator.persistence.BatchRelationshipConst.PARENT;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Neo4jJobExecution {

    public static final Neo4jJobExecution.FromBatch MAP = Mappers.getMapper(Neo4jJobExecution.FromBatch.class);

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = PARENT, direction = OUTGOING)
    private Neo4jJobInstance jobInstance;

    @Relationship(type = PARENT, direction = INCOMING)
    private Collection<Neo4jStepExecution> persistentStepExecutions;

    private JobParameters jobParameters;
    private BatchStatus status = BatchStatus.STARTING;
    private Date startTime = null;
    private Date createTime = new Date(System.currentTimeMillis());
    private Date endTime = null;
    private Date lastUpdated = null;
    private ExitStatus exitStatus = ExitStatus.UNKNOWN;
    private String jobConfigurationName;
    
    private Map<String, Object> executionContext;

    @Mapper
    public interface FromBatch {

        @Mapping(source = "stepExecutions", target = "persistentStepExecutions")
        Neo4jJobExecution map(JobExecution batch);

        JobExecution map(Neo4jJobExecution source, @MappingTarget JobExecution target);

        @Mapping(source = "persistentStepExecutions", target = "stepExecutions")
        default JobExecution map(Neo4jJobExecution source) {
            return map(source, new JobExecution(source.getId()));
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

        default JobInstance map(Neo4jJobInstance batch) {
            return new JobInstance(batch.getId(), batch.getJobName());
        }

    }
}
