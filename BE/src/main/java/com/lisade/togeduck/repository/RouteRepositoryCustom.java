package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.RouteDetailDao;
import com.lisade.togeduck.dto.response.UserReservedRouteDto;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RouteRepositoryCustom {

    Optional<RouteDetailDao> findRouteDetail(Long routeId);

    Slice<UserReservedRouteDto> findReservedRoutes(Pageable pageable, Long userId);
}
