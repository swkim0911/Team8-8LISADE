package com.lisade.togeduck.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class FestivalTotalSeatDto {

    Long id;
    Integer totalSeats;
    Integer reservedSeats;
    LocalDateTime createdAt;

    public FestivalTotalSeatDto(Long id, Integer notReservedSeats, Integer reservedSeats,
        LocalDateTime createdAt) {
        this.id = id;
        this.totalSeats = notReservedSeats + reservedSeats;
        this.reservedSeats = reservedSeats;
        this.createdAt = createdAt;
    }
}
