package com.lisade.togeduck.batch;

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
public class FestivalItem {

    private Long id;
    private Long weeklyViews;
    private Long totalSeats;
    private Long totalReservationSeats;
    private LocalDateTime createdAt;
}
