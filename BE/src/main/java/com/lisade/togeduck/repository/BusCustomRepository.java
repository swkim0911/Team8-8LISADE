package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.BusLayoutResponse;
import com.lisade.togeduck.dto.response.DistancePricesResponse.BusInfo;
import java.util.List;

public interface BusCustomRepository {

    List<BusInfo> findBusInfoByDistance(Integer distance);

    BusLayoutResponse findBusLayoutByRouteId(Long routeId);
}
