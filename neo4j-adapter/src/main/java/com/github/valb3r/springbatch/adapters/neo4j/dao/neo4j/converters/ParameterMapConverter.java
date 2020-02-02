package com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.neo4j.ogm.typeconversion.AttributeConverter;
import org.springframework.batch.core.JobParameter;

import java.util.Map;

public class ParameterMapConverter implements AttributeConverter<Map<String, JobParameter>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String toGraphProperty(Map<String, JobParameter> value) {
        return mapper.writeValueAsString(value);
    }

    @Override
    @SneakyThrows
    public Map<String, JobParameter> toEntityAttribute(String value) {
        return mapper.readerFor(new TypeReference<Map<String, JobParameter>>() {}).readValue(value);
    }
}
