package com.lisade.togeduck.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FestivalDto {

    Long id;
    String title;
    String location;
    LocalDate startedAt;
    List<String> path;
}
