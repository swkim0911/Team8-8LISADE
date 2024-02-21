package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QFestival.festival;
import static com.lisade.togeduck.entity.QFestivalView.festivalView;

import com.lisade.togeduck.dto.response.BestFestivalDto;
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
}
