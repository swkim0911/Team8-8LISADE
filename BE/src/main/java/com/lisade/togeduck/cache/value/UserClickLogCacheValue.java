package com.lisade.togeduck.cache.value;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash("user_click_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserClickLogCacheValue {

    @Id
    private String id;
    @Indexed
    private String userId;
    private List<Long> festivalIds;

    private UserClickLogCacheValue(String userId) {
        this.userId = userId;
        this.festivalIds = new ArrayList<>();
    }

    public static UserClickLogCacheValue of(String userId) {
        return new UserClickLogCacheValue(userId);
    }

    public void addFestivalId(Long festivalId) {
        festivalIds.add(festivalId);
    }
}
