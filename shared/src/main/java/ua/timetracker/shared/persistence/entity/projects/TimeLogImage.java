package ua.timetracker.shared.persistence.entity.projects;

import lombok.*;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.LOGGED_FOR;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class TimeLogImage {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = LOGGED_FOR)
    private TimeLog owner;

    private String imageUrl;
    private String relPhysicalPath;

    private Duration duration;
    private LocalDateTime timestamp;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public String newPath(String tag) {
        return String.format("%s-%d-%s.jpg", tag, owner.getId(), UUID.randomUUID().toString());
    }

    public Path physicalFile(String basePath) {
        return physicalFile(basePath, relPhysicalPath);
    }

    public static Path physicalFile(String basePath, String relPhysicalPath) {
        return Paths.get(basePath).resolve(relPhysicalPath);
    }
}
