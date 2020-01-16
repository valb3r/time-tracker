package ua.timetracker.reportgenerator.config.neo4jbatch.dao.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.neo4j.ogm.typeconversion.AttributeConverter;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

import java.util.Map;

public class ParametersConverter implements AttributeConverter<JobParameters, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String toGraphProperty(JobParameters value) {
        return mapper.writeValueAsString(value.getParameters());
    }

    @Override
    @SneakyThrows
    public JobParameters toEntityAttribute(String value) {
        return new JobParameters(mapper.readerFor(new TypeReference<Map<String, JobParameter>>() {}).readValue(value));
    }
}
