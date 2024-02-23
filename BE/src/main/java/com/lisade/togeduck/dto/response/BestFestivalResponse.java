package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BestFestivalResponse {

    Integer totalSize;
    List<Banner> banner;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Banner {

        Long id;
        String thumbnailPath;
        String city;
        String title;
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
        LocalDateTime startedAt;
        String location;
    }
}
