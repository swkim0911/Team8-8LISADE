package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.enums.Category;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalRepository extends JpaRepository<Festival, Long> {

    Slice<Festival> findSliceByCategoryAndStatus(final Pageable pageable, Category category,
        FestivalStatus festivalStatus);
}
