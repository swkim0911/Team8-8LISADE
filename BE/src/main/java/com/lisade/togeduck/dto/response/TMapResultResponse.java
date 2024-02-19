package com.lisade.togeduck.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TMapResultResponse {

    private String type;
    private List<Features> features;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Features {

        private String type;
        private Properties properties;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {

        private Integer totalDistance;
        private Integer totalTime;
        private Integer totalFare;
        private Integer taxiFare;
    }
}
