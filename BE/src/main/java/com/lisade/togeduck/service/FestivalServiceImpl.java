package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalDetailResponse;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.dto.response.FestivalRoutesResponse;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import com.lisade.togeduck.exception.FestivalNotFoundException;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.FestivalRepository;
import com.lisade.togeduck.repository.RouteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FestivalServiceImpl implements FestivalService {

    private final ViewService viewService;
    private final FestivalRepository festivalRepository;
    private final RouteRepository routeRepository;
    private final FestivalMapper festivalMapper;

    @Override
    public Slice<FestivalResponse> getList(Pageable pageable, Long categoryId,
        FestivalStatus festivalStatus,
        String filterType) {
        Slice<Festival> festivals = festivalRepository.findSliceByCategoryAndFestivalStatus(
            pageable, categoryId, festivalStatus);
        return festivalMapper.toFestivalResponseSlice(festivals);
    }

    @Override
    public Festival get(Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        return optionalFestival.orElseThrow(FestivalNotFoundException::new);
    }

    @Override
    @Transactional
    public FestivalDetailResponse getDetail(Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        Festival festival = optionalFestival.orElseThrow(FestivalNotFoundException::new);
        viewService.add(festival);
        return festivalMapper.toFestivalDetailResponse(festival);
    }

    @Override
    public BestFestivalResponse getBest() {
        Integer defaultLimit = 8;
        List<BestFestivalDto> best = festivalRepository.findBest(defaultLimit);
        return festivalMapper.toBestFestivalResponse(best.size(), best);
    }

    @Override
    public Slice<FestivalRoutesResponse> getRoutes(Pageable pageable, Long id, String cityName) {
        return routeRepository.findRoutes(pageable, id, cityName);
    }
}
