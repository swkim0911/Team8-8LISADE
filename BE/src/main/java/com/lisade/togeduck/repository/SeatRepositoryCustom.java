package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.SeatResponse;
import java.util.List;

public interface SeatRepositoryCustom {

    List<SeatResponse> findSeatsByRouteId(Long routeId);
}
