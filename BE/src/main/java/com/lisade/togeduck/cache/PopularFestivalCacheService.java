package com.lisade.togeduck.cache;

import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.exception.FestivalNotFoundException;
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

    public PopularFestivalCacheValue get(String categoryId) {
        Optional<PopularFestivalCacheValue> optionalPopularFestivalCacheValue = popularFestivalCacheRepository.findById(
            categoryId);
        return optionalPopularFestivalCacheValue.orElseThrow(
            FestivalNotFoundException::new);
    }
}
