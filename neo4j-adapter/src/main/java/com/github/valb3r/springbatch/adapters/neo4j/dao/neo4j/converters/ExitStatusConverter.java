package com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.neo4j.ogm.typeconversion.AttributeConverter;
import org.springframework.batch.core.ExitStatus;

public class ExitStatusConverter implements AttributeConverter<ExitStatus, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String toGraphProperty(ExitStatus value) {
        return mapper.writeValueAsString(new ExitStatusMapped(value));
    }

    @Override
    @SneakyThrows
    public ExitStatus toEntityAttribute(String value) {
        return mapper.readValue(value, ExitStatusMapped.class).asStatus();
    }

    @Data
    @NoArgsConstructor
    private static class ExitStatusMapped {

        private String exitCode;
        private String exitDescription;

        ExitStatusMapped(ExitStatus value) {
            this.exitCode = value.getExitCode();
            this.exitDescription = value.getExitDescription();
        }

        ExitStatus asStatus() {
            return new ExitStatus(exitCode, exitDescription);
        }
    }
}
