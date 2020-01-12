package ua.timetracker.shared.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PathEntry<T> {

    private String path;
    private T entry;
}
