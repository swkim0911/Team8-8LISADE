package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FestivalDto {

    private Long id;
    private String title;
    private String location;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startedAt;
    private List<String> paths;
}
