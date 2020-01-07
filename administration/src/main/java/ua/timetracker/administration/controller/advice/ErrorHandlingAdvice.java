package ua.timetracker.administration.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ua.timetracker.shared.restapi.EntityNotFoundException;
import ua.timetracker.shared.restapi.ForbiddenException;

@ControllerAdvice(annotations = RestController.class)
public class ErrorHandlingAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handle(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
