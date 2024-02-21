package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long>,
    FestivalRepositoryCustom {

    @Query("SELECT f FROM Festival f LEFT JOIN FETCH f.category c WHERE c.id = :categoryId AND f.festivalStatus = :festivalStatus")
    Slice<Festival> findSliceByCategoryAndFestivalStatus(@Param("pageable") Pageable pageable,
        @Param("categoryId") Long categoryId,
        @Param("festivalStatus") FestivalStatus festivalStatus);

    @Override
    @Query("SELECT f FROM Festival f LEFT JOIN FETCH f.festivalImages LEFT JOIN FETCH f.category WHERE f.id = :id")
    Optional<Festival> findById(Long id);

}
