package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDto;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RouteRepositoryCustom {


    Optional<RouteDetailDto> findRouteDetail(Long routeId);

    Slice<UserReservedRouteDto> findReservedRoutes(Pageable pageable, Long userId);
}
