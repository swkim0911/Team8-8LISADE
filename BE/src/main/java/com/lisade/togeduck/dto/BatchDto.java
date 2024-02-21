package com.lisade.togeduck.dto;

import java.time.LocalDateTime;
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
public class BatchDto {

    private Long id;
    private String title;
    private Long weeklyViews;
    private Long totalSeats;
    private Long totalReservationSeats;
    private LocalDateTime createdAt;
}
