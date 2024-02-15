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
public class DistancePricesDto {

    private Integer distance;
    private Integer expectedTime;
    List<BusInfo> busInfos;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusInfo {

        private Long id;
        private String busType;
        private Integer price;
    }
}
