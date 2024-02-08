package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalRepository extends JpaRepository<Festival, Long> {

    Slice<Festival> findSliceByCategoryAndStatus(final Pageable pageable, Category category,
        Status status);
}
