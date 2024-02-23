package com.lisade.togeduck.util;

import com.lisade.togeduck.cache.BestFestivalCacheService;
import com.lisade.togeduck.cache.PopularFestivalCacheService;
import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.CategoryRepository;
import com.lisade.togeduck.repository.FestivalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
@RequiredArgsConstructor
public class PopularFestivalCacheUpdateTasklet implements Tasklet {

    public static final int BEST_FESTIVAL_LIMIT = 8;
    private final FestivalRepository festivalRepository;
    private final CategoryRepository categoryRepository;
    private final PopularFestivalCacheService popularFestivalCacheService;
    private final BestFestivalCacheService bestFestivalCacheService;
    private final FestivalMapper festivalMapper;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            List<FestivalResponse> festivalsByCategorySortedByScore = festivalRepository.findFestivalsByCategorySortedByScore(
                category.getId(), 100);
            popularFestivalCacheService.save(category.getId().toString(),
                festivalsByCategorySortedByScore);
            log.info("카테고리 인기순위 업데이트 {}", category.getId());
        }
        List<BestFestivalDto> best = festivalRepository.findBest(BEST_FESTIVAL_LIMIT);
        BestFestivalResponse bestFestivalResponse = festivalMapper.toBestFestivalResponse(
            BEST_FESTIVAL_LIMIT, best);
        bestFestivalCacheService.save(bestFestivalResponse);
        log.info("인기 게시물 업데이트 완료");
        return RepeatStatus.FINISHED;
    }
}
