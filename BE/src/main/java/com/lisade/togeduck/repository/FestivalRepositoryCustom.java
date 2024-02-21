package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.FestivalTotalSeatDto;
import com.lisade.togeduck.dto.response.FestivalViewDto;
import java.util.List;

public interface FestivalRepositoryCustom {

    List<BestFestivalDto> findBest(Integer limit);

    List<FestivalViewDto> findFestivalsWithView(Integer week, Long categoryId);

    List<FestivalTotalSeatDto> getTotalSeat(Long categoryId);
}
