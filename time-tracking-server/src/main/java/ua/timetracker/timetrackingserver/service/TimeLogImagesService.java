package ua.timetracker.timetrackingserver.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLogImage;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogImagesRepository;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogImageDto;
import ua.timetracker.timetrackingserver.config.ImageUploadConfig;

@Service
@RequiredArgsConstructor
public class TimeLogImagesService {

    private final TimeLogsRepository logs;
    private final TimeLogImagesRepository images;
    private final ImageUploadConfig uploadConfig;

    public Mono<TimeLogImageDto> uploadTimelogImage(long userId, long cardId, String tag, FilePart file) {
        return logs.findByIdAndUserId(cardId, userId).flatMap(it -> {
            val image = new TimeLogImage();
            image.setOwner(it);
            image.setRelPhysicalPath(image.newPath(tag));
            file.transferTo(image.physicalFile(uploadConfig.getPath()));
            return images.save(image);
        }).map(TimeLogImageDto.MAP::map);
    }
}
