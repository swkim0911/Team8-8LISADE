package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.RouteDetailDao;
import java.util.Optional;

public interface RouteRepositoryCustom {

    Optional<RouteDetailDao> findRouteDetail(Long routeId);
}
