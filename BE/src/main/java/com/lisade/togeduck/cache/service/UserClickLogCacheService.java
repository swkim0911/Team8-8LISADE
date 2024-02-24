package com.lisade.togeduck.cache.service;

import com.lisade.togeduck.cache.repository.UserClickLogCacheRepository;
import com.lisade.togeduck.cache.value.UserClickLogCacheValue;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClickLogCacheService {

    private final UserClickLogCacheRepository userClickLogCacheRepository;

    public boolean isClick(String userId, Long festivalId) {
        Optional<UserClickLogCacheValue> userClickLogCacheValue = get(userId);

        return userClickLogCacheValue.isPresent() &&
            userClickLogCacheValue.get().getFestivalIds().contains(festivalId);
    }

    public void addFestival(String userId, Long festivalId) {
        Optional<UserClickLogCacheValue> userClickLogCacheValue = get(userId);

        if (userClickLogCacheValue.isPresent()) {
            userClickLogCacheValue.get().addFestivalId(festivalId);
            save(userClickLogCacheValue.get());

            return;
        }

        UserClickLogCacheValue newUserClickLogCacheValue = UserClickLogCacheValue.of(userId);
        newUserClickLogCacheValue.addFestivalId(festivalId);

        save(newUserClickLogCacheValue);
    }

    public void save(UserClickLogCacheValue userClickLogCacheValue) {
        userClickLogCacheRepository.save(userClickLogCacheValue);
    }

    public Optional<UserClickLogCacheValue> get(String userId) {
        return userClickLogCacheRepository.findByUserId(userId);
    }
}
