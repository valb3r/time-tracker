package ua.timetracker.reportgenerator.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Neo4jJobInstance {

    public static final Neo4jJobInstance.FromBatch MAP = Mappers.getMapper(Neo4jJobInstance.FromBatch.class);

    @Id
    @GeneratedValue
    private Long id;

    private String jobName;
    private String jobKey;
    private Map<String, JobParameter> parameters;

    @CreatedDate
    private LocalDateTime createdAt;

    @Mapper
    public interface FromBatch {
        Neo4jJobInstance map(JobInstance batch);

        default JobInstance map(Neo4jJobInstance batch) {
            return new JobInstance(batch.getId(), batch.getJobName());
        }
    }
}
