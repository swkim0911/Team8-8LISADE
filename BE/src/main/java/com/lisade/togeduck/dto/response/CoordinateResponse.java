package com.lisade.togeduck.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateResponse {

    Double sourceXPos;
    Double sourceYPos;
    Double destinationXPos;
    Double destinationYPos;
}
