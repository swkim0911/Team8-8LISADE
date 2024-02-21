package com.lisade.togeduck.dto.response;

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

    public FestivalTotalSeatDto(Long id, Integer notReservedSeats, Integer reservedSeats) {
        this.id = id;
        this.totalSeats = notReservedSeats + reservedSeats;
        this.reservedSeats = reservedSeats;
    }
}
