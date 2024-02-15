package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import java.util.List;

public interface FestivalRepositoryCustom {

    List<BestFestivalDto> findBest(Integer limit);
}
