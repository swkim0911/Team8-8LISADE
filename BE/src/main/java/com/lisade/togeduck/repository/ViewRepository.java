package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QFestivalView.festivalView;

import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.FestivalView;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ViewRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public void add(Festival festival, LocalDate measurementAt) {
        long affectedRows = updateCount(festival, measurementAt);

        if (affectedRows == 0) {
            create(festival, measurementAt);
        }
    }

    private void create(Festival festival, LocalDate measurementAt) {
        FestivalView festivalViewEntity = FestivalView.builder().count(1)
            .measurementAt(measurementAt).festival(festival)
            .build();
        entityManager.persist(festivalViewEntity);
    }

    private long updateCount(Festival festival, LocalDate measurementAt) {
        return queryFactory.update(festivalView)
            .set(festivalView.count, festivalView.count.add(1))
            .where(festivalView.festival.id.eq(festival.getId())
                .and(festivalView.measurementAt.eq(measurementAt)))
            .execute();
    }
}
