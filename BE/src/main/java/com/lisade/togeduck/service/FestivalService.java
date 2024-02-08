package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.FestivalDetailDto;
import com.lisade.togeduck.dto.FestivalDto;
import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FestivalService {

    Slice<FestivalDto> getList(Pageable pageable, Category category, Status status,
        String filterType);

    Festival get(Long id);

    FestivalDetailDto getDetail(Long id);
}
