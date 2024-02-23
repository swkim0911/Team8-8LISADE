package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.FestivalResponse;
import java.util.List;

public interface FestivalRepositoryCustom {

    List<BestFestivalDto> findBest(Integer limit);

    List<FestivalResponse> findFestivalsByCategorySortedByScore(Long categoryId, Integer limit);
}
