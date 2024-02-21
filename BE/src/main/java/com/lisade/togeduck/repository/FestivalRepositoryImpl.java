package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QFestival.festival;
import static com.lisade.togeduck.entity.QFestivalView.festivalView;
import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QSeat.seat;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.FestivalTotalSeatDto;
import com.lisade.togeduck.dto.response.FestivalViewDto;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class FestivalRepositoryImpl implements FestivalRepositoryCustom {

    LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BestFestivalDto> findBest(Integer limit) {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        return queryFactory.select(Projections.constructor(BestFestivalDto.class,
                festival.id,
                festival.thumbnailPath
            ))
            .from(festivalView)
            .leftJoin(festival).on(festival.id.eq(festivalView.festival.id)
                .and(festivalView.measurementAt.after(oneWeekAgo)))
            .groupBy(festival.id)
            .limit(limit)
            .orderBy(festivalView.count.sum().desc())
            .fetch();
    }

    @Override
    public List<FestivalViewDto> findFestivalsWithView(Integer week, Long categoryId) {
        LocalDate onWeekAgo = LocalDate.now().minusWeeks(week);

        return queryFactory.selectDistinct(Projections.constructor(FestivalViewDto.class,
                festival.id,
                festival.title,
                festival.location,
                festival.category.id,
                festival.startedAt,
                festival.createdAt,
                festival.thumbnailPath,
                festivalView.count.sum().coalesce(0)
            ))
            .from(festival)
            .join(festivalView)
            .on(festival.id.eq(festivalView.festival.id))
            .groupBy(festival.id)
            .where(festival.festivalStatus.eq(FestivalStatus.RECRUITMENT)
                .and(festivalView.measurementAt.after(onWeekAgo))
                .and(festival.category.id.eq(categoryId)))
            .fetch();
    }

    @Override
    public List<FestivalTotalSeatDto> getTotalSeat(Long categoryId) {
        List<FestivalTotalSeatDto> fetch = queryFactory.selectDistinct(
                Projections.constructor(FestivalTotalSeatDto.class,
                    festival.id,
                    seat.status.when(SeatStatus.AVAILABLE).then(1).otherwise(0).sum(),
                    seat.status.when(SeatStatus.RESERVATION).then(1).otherwise(0).sum(),
                    festival.createdAt))
            .from(festival)
            .join(festival.routes, route)
            .join(route.seats, seat)
            .where(festival.category.id.eq(categoryId))
            .groupBy(festival.id)
            .fetch();
        log.info(fetch.toString());
        return fetch;
    }
}
