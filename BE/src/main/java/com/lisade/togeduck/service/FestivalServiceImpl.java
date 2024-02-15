package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalDetailDto;
import com.lisade.togeduck.dto.response.FestivalDto;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import com.lisade.togeduck.exception.NotFoundException;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.FestivalRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FestivalServiceImpl implements FestivalService {

    private final FestivalRepository festivalRepository;
    private final FestivalMapper festivalMapper;
    private final ViewService viewService;

    @Override
    public Slice<FestivalDto> getList(Pageable pageable, Long categoryId,
        FestivalStatus festivalStatus,
        String filterType) {
        Slice<Festival> festivals = festivalRepository.findSliceByCategoryAndFestivalStatus(
            pageable, categoryId, festivalStatus);
        return festivalMapper.toFestivalDtoSlice(festivals);
    }

    @Override
    public Festival get(Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        return optionalFestival.orElseThrow(NotFoundException::new);
    }

    @Override
    public FestivalDetailDto getDetail(Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        Festival festival = optionalFestival.orElseThrow(NotFoundException::new);
        viewService.add(festival);
        return festivalMapper.toFestivalDetailDto(festival);
    }

    @Override
    public BestFestivalResponse getBest() {
        Integer defaultLimit = 8;
        List<BestFestivalDto> best = festivalRepository.findBest(defaultLimit);
        return festivalMapper.toBestFestivalResponse(best.size(), best);
    }
}
