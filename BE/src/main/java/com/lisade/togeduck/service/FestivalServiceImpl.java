package com.lisade.togeduck.service;

import com.lisade.togeduck.cache.BestFestivalCacheService;
import com.lisade.togeduck.cache.BestFestivalCacheValue;
import com.lisade.togeduck.cache.FestivalClickCountCacheService;
import com.lisade.togeduck.cache.PopularFestivalCacheService;
import com.lisade.togeduck.cache.PopularFestivalCacheValue;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalDetailResponse;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.dto.response.FestivalRoutesResponse;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import com.lisade.togeduck.exception.FestivalNotFoundException;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.FestivalRepository;
import com.lisade.togeduck.repository.RouteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FestivalServiceImpl implements FestivalService {

    private final FestivalClickCountCacheService festivalClickCountCacheService;
    private final PopularFestivalCacheService popularFestivalCacheService;
    private final BestFestivalCacheService bestFestivalCacheService;
    private final CategoryService categoryService;
    private final FestivalRepository festivalRepository;
    private final RouteRepository routeRepository;
    private final FestivalMapper festivalMapper;

    @Override
    public Slice<FestivalResponse> getList(Pageable pageable, Long categoryId,
        FestivalStatus festivalStatus,
        String filterType) {
        if (filterType.equals("best")) {
            PopularFestivalCacheValue popularFestivalCacheValue = popularFestivalCacheService.get(
                categoryId.toString());
            return getFestivalResponseSlice(
                pageable, popularFestivalCacheValue);

        } else {
            Slice<Festival> festivals = festivalRepository.findSliceByCategoryAndFestivalStatus(
                pageable, categoryId, festivalStatus);
            return festivalMapper.toFestivalResponseSlice(festivals);
        }
    }

    private Slice<FestivalResponse> getFestivalResponseSlice(Pageable pageable,
        PopularFestivalCacheValue popularFestivalCacheValue) {
        List<FestivalResponse> festivalResponses = popularFestivalCacheValue.getFestivalResponses();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), festivalResponses.size());

        List<FestivalResponse> slicedFestivalResponses = festivalResponses.subList(start, end);
        boolean hasNext = end < festivalResponses.size();

        // Slice 객체 생성 및 반환
        return new SliceImpl<>(slicedFestivalResponses,
            pageable, hasNext);
    }

    @Override
    public Festival get(Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        return optionalFestival.orElseThrow(FestivalNotFoundException::new);
    }

    @Override
    @Transactional
    public FestivalDetailResponse getDetail(User user, Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        Festival festival = optionalFestival.orElseThrow(FestivalNotFoundException::new);

        festivalClickCountCacheService.increase(user.getUserId(), id);

        return festivalMapper.toFestivalDetailResponse(festival);
    }

    @Override
    public BestFestivalResponse getBest() {
        BestFestivalCacheValue bestFestivalCacheValue = bestFestivalCacheService.get();
        return bestFestivalCacheValue.getBests();
    }

    @Override
    public Slice<FestivalRoutesResponse> getRoutes(Pageable pageable, Long id, String cityName) {
        return routeRepository.findRoutes(pageable, id, cityName);
    }
}
