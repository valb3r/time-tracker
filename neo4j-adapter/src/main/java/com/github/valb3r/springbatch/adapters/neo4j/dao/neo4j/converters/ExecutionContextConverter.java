package com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.Map;

public class ExecutionContextConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String toGraphProperty(Map<String, Object> value) {
        return mapper.writeValueAsString(value);
    }

    @Override
    @SneakyThrows
    public Map<String, Object> toEntityAttribute(String value) {
        return mapper.readerFor(new TypeReference<Map<String, Object>>() {}).readValue(value);
    }
}
