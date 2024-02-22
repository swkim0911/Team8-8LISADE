package com.lisade.togeduck.cache;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FestivalClickCountCacheService {

    private final UserClickLogCacheService userClickLogCacheService;
    private final FestivalClickCountCacheRepository festivalClickCountCacheRepository;

    public void increase(String userId, Long festivalId) {
        if (userClickLogCacheService.isClick(userId, festivalId)) {
            return;
        }

        userClickLogCacheService.addFestival(userId, festivalId);

        Optional<FestivalClickCountCacheValue> festivalClickCountCacheValue =
            festivalClickCountCacheRepository.findByFestivalId(festivalId);

        if (festivalClickCountCacheValue.isEmpty()) {
            save(FestivalClickCountCacheValue.of(festivalId, 1));

            return;
        }

        festivalClickCountCacheValue.get().increaseClickCount();
        save(festivalClickCountCacheValue.get());
    }

    public Iterable<FestivalClickCountCacheValue> getAll() {
        return festivalClickCountCacheRepository.findAll();
    }

    public void save(FestivalClickCountCacheValue festivalClickCountCacheValue) {
        festivalClickCountCacheRepository.save(festivalClickCountCacheValue);
    }

    public void deleteAll() {
        festivalClickCountCacheRepository.deleteAll();
    }
}
