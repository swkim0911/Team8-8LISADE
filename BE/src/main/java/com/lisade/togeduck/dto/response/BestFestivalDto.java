package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BestFestivalDto {

    Long id;
    String thumbnailPath;
    String city;
    String title;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime startedAt;
    String location;

    public BestFestivalDto(Long id, String thumbnailPath, String city, String title,
        LocalDateTime startedAt,
        String location) {
        this.id = id;
        this.thumbnailPath = thumbnailPath;
        this.city = city;
        this.title = title;
        this.startedAt = startedAt;
        this.location = location;
    }
}
