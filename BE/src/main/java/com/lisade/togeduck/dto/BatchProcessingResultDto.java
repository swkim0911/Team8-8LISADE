package com.lisade.togeduck.dto;

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
public class BatchProcessingResultDto {

    private Long id;
    private Double score;

    public static BatchProcessingResultDto of(Long id, Double score) {
        return new BatchProcessingResultDto(id, score);
    }
}
