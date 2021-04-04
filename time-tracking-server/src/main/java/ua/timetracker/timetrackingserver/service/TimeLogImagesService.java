package ua.timetracker.timetrackingserver.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLogImage;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogImagesRepository;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogImageDto;
import ua.timetracker.timetrackingserver.config.ImageUploadConfig;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class TimeLogImagesService {

    private final TimeLogsRepository logs;
    private final TimeLogImagesRepository images;
    private final ImageUploadConfig uploadConfig;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<TimeLogImageDto> uploadTimelogImage(long userId, long cardId, String tag, FilePart file) {
        return logs.findByIdAndUserId(cardId, userId).flatMap(it -> logs.findById(it.getId()) /* Direct result usage removes relationships, so loading it*/).map(it -> {
            val image = new TimeLogImage();
            image.setOwner(it);
            image.setRelPhysicalPath(image.newPath(tag));
            image.setImageUrl(image.getRelPhysicalPath());
            file.transferTo(image.physicalFile(uploadConfig.getPath()));
            return image;
        }).flatMap(it -> images.save(it).map(TimeLogImageDto.MAP::map));
    }
}
