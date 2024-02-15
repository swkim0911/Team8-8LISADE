package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.RouteDetailDto;
import java.util.Optional;

public interface RouteRepositoryCustom {

    Optional<RouteDetailDto> findRouteDetail(Long routeId);
}
