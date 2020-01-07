package ua.timetracker.shared.restapi;

import reactor.core.publisher.Mono;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Forbidden");
    }

    public static <T> Mono<T> mono() {
        return Mono.error(new ForbiddenException());
    }
}
