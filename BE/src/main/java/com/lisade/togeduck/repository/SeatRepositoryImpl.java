package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QSeat.seat;

import com.lisade.togeduck.dto.response.SeatResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SeatRepositoryImpl implements SeatRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SeatResponse> findSeatsByRouteId(Long routeId) {
        return queryFactory.select(Projections.constructor(
                SeatResponse.class,
                seat.id, seat.no, seat.status))
            .from(route)
            .join(seat)
            .on(seat.route.id.eq(route.id))
            .where(route.id.eq(routeId))
            .fetch();
    }
}
