package ua.timetracker.shared.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.springframework.data.annotation.CreatedDate;
import ua.timetracker.shared.restapi.dto.TimeLogUpload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@Node
@NoArgsConstructor
public class TimeLog {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private Collection<String> tags;

    @NotNull
    private Duration duration;

    @CreatedDate
    private LocalDateTime loggedAtUtc;

    public TimeLog(TimeLogUpload upload) {
        tags = upload.getTags();
        duration = upload.getDuration();
    }
}
