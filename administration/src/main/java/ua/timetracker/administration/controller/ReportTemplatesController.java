package ua.timetracker.administration.controller;

import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.report.ReportTemplate;
import ua.timetracker.shared.persistence.repository.reactive.ReportTemplatesRepository;
import ua.timetracker.shared.restapi.dto.report.ReportTemplateDto;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Base64;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.restapi.Paths.V1_REPORT_TEMPLATES;

@RestController
@RequestMapping(value = V1_REPORT_TEMPLATES)
@RequiredArgsConstructor
public class ReportTemplatesController {

    private final ReportTemplatesRepository templates;

    @SneakyThrows
    @PutMapping(path = "/{description}", consumes = MULTIPART_FORM_DATA_VALUE)
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ReportTemplateDto> createTemplate(
        @PathVariable("description") String templateDescription,
        @RequestPart("file") FilePart file
    ) {
        return file.content().reduce(
            new InputStream() {
                public int read() {
                    return -1;
                }
            },
            (InputStream is, DataBuffer data) -> new SequenceInputStream(is, data.asInputStream())
        ).flatMap(it -> newReportTemplate(templateDescription, it)).map(ReportTemplateDto.MAP::map);
    }

    @SneakyThrows
    private Mono<ReportTemplate> newReportTemplate(String templateDescription, InputStream is) {
        return templates.save(
            ReportTemplate.builder()
                .description(templateDescription)
                .template(Base64.getEncoder().encodeToString(ByteStreams.toByteArray(is)))
                .build()
        );
    }

    @GetMapping
    @Transactional(REACTIVE_TX_MANAGER)
    public Flux<ReportTemplateDto> availableTemplates() {
        return templates.findAll().map(ReportTemplateDto.MAP::map);
    }

    @GetMapping("/{id}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> downloadTemplate(
        @PathVariable("id") long templateId,
        ServerHttpResponse response
    ) {
        return templates.findById(templateId).flatMap(it -> {
            ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
            response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + it.getDescription() + ".xlsx");
            response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
            byte[] value = Base64.getDecoder().decode(it.getTemplate());
            return zeroCopyResponse.writeWith(
                DataBufferUtils.read(
                    new ByteArrayResource(value), new DefaultDataBufferFactory(), value.length
                )
            );
        });
    }

    @DeleteMapping("/{id}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> deleteTemplate(
        @PathVariable("id") long templateId
    ) {
        return templates.deleteById(templateId);
    }
}
