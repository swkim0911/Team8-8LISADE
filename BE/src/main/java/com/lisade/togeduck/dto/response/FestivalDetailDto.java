package com.lisade.togeduck.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FestivalDetailDto {

    private Long id;
    private String title;
    private String category;
    private String location;
    private String content;
    private List<String> paths;
    private LocalDate startedAt;
}
