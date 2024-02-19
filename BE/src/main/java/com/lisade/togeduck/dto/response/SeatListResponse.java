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
public class SeatListResponse {

    private Integer numberOfSeats;
    private Integer row;
    private Integer col;
    private Integer backSeats;
    private List<SeatResponse> seats;
}
