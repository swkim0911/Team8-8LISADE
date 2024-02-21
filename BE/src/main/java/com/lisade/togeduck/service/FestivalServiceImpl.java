package com.lisade.togeduck.service;

import com.lisade.togeduck.cache.FestivalClickCountCacheService;
import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalDetailResponse;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.dto.response.FestivalRoutesResponse;
import com.lisade.togeduck.dto.response.FestivalTotalSeatDto;
import com.lisade.togeduck.dto.response.FestivalViewDto;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import com.lisade.togeduck.exception.FestivalNotFoundException;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.FestivalRepository;
import com.lisade.togeduck.repository.RouteRepository;
import com.mysema.commons.lang.Pair;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FestivalServiceImpl implements FestivalService {

    public static final double GRAVITY_CONSTANT = 1.8;
    private final FestivalClickCountCacheService festivalClickCountCacheService;
    private final FestivalRepository festivalRepository;
    private final RouteRepository routeRepository;
    private final FestivalMapper festivalMapper;
    private final CategoryService categoryService;

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
    public FestivalDetailResponse getDetail(User user, Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        Festival festival = optionalFestival.orElseThrow(FestivalNotFoundException::new);

        festivalClickCountCacheService.increase(user.getUserId(), id);

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

    @Override
    @Transactional
    public Map<Long, List<FestivalResponse>> calculate() {
        Map<Long, List<FestivalResponse>> sortedFestivalWithCategory = new HashMap<>();

        List<Long> categoryIds = categoryService.getIds();
        for (Long categoryId : categoryIds) {
            List<Pair<FestivalResponse, Double>> result = new ArrayList<>();

            List<FestivalViewDto> weeklyView = festivalRepository.findFestivalsWithView(1,
                categoryId);
            List<FestivalTotalSeatDto> totalSeat = festivalRepository.getTotalSeat(categoryId);

            setSortedFestivalWithCategory(categoryId, weeklyView, totalSeat, result,
                sortedFestivalWithCategory);
        }

        return sortedFestivalWithCategory;
    }

    private void setSortedFestivalWithCategory(Long categoryId, List<FestivalViewDto> weeklyView,
        List<FestivalTotalSeatDto> totalSeat, List<Pair<FestivalResponse, Double>> result,
        Map<Long, List<FestivalResponse>> sortedFestivalWithCategory) {
        for (int i = 0; i < weeklyView.size(); i++) {
            Double score = getTotalScore(weeklyView, i, totalSeat);

            FestivalResponse festivalResponse = festivalMapper.toFestivalResponse(
                weeklyView.get(i));
            result.add(new Pair<>(festivalResponse, score));
        }
        sortFestivalResponseByScore(result);
        List<FestivalResponse> list = result.stream().map(Pair::getFirst).toList();

        sortedFestivalWithCategory.put(categoryId, list);
    }

    private void sortFestivalResponseByScore(List<Pair<FestivalResponse, Double>> result) {
        result.sort(new Comparator<Pair<FestivalResponse, Double>>() {
            @Override
            public int compare(Pair<FestivalResponse, Double> o1,
                Pair<FestivalResponse, Double> o2) {
                return o2.getSecond().compareTo(o1.getSecond());
            }
        });
    }

    private Double getTotalScore(List<FestivalViewDto> weeklyView, int i,
        List<FestivalTotalSeatDto> totalSeat) {
        Double viewScore = getViewScore(weeklyView.get(i));
        Double reservationScore = getReservationRatioScore(totalSeat.get(i));
        return viewScore + reservationScore;
    }

    private Double getReservationRatioScore(FestivalTotalSeatDto festivalTotalSeatDto) {
        Integer totalSeats = festivalTotalSeatDto.getTotalSeats();
        Integer reservedSeats = festivalTotalSeatDto.getReservedSeats();
        if (totalSeats == null || totalSeats == 0) {
            return 0.0;
        }

        return (double) reservedSeats / totalSeats;
    }

    private Double getViewScore(FestivalViewDto festivalViewDto) {
        LocalDateTime createdAt;
        createdAt = festivalViewDto.getCreatedAt();
        Instant instant = createdAt.toInstant(ZoneOffset.UTC);
        long hours = instant.getEpochSecond() / 3600;

        Integer weeklyViews = festivalViewDto.getWeeklyViews();
        hours += 2;
        Double time = Math.pow(hours, GRAVITY_CONSTANT);

        return weeklyViews / time;
    }
}
