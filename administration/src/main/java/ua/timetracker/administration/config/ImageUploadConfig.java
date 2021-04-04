package ua.timetracker.administration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Data
@Configuration
@ConfigurationProperties(prefix = "image-upload")
public class ImageUploadConfig {

    @NotBlank
    private String path;

    // Image could be uploaded to 3rd party resource - i.e. S3
    @NotBlank
    private String baseUrl;
}