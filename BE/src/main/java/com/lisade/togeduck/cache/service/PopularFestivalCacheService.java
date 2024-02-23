package com.lisade.togeduck.cache.service;

import com.lisade.togeduck.cache.repository.PopularFestivalCacheRepository;
import com.lisade.togeduck.cache.value.PopularFestivalCacheValue;
import com.lisade.togeduck.dto.response.FestivalResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularFestivalCacheService {

    private final PopularFestivalCacheRepository popularFestivalCacheRepository;

    public void save(String category, List<FestivalResponse> festivalResponses) {
        PopularFestivalCacheValue popularFestivalCacheValue = new PopularFestivalCacheValue(
            category, festivalResponses);
        popularFestivalCacheRepository.save(popularFestivalCacheValue);
    }

    public Optional<PopularFestivalCacheValue> get(String categoryId) {
        return popularFestivalCacheRepository.findById(categoryId);
    }
}
