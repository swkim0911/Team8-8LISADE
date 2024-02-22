package com.lisade.togeduck.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FestivalItemProcessingResult {

    private Long id;
    private Double score;

    public static FestivalItemProcessingResult of(Long id, Double score) {
        return new FestivalItemProcessingResult(id, score);
    }
}
