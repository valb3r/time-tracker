package ua.timetracker.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PathEntry<T> {

    private String path;
    private T entry;
}
