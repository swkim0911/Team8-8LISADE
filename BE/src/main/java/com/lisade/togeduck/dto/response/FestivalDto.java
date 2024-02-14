package com.lisade.togeduck.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FestivalDto {

    private Long id;
    private String title;
    private String location;
    private LocalDate startedAt;
    private List<String> paths;
}