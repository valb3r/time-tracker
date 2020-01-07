package ua.timetracker.shared.restapi;

import reactor.core.publisher.Mono;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Entity not found");
    }

    public static <T> Mono<T> mono() {
        return Mono.error(new EntityNotFoundException());
    }
}
