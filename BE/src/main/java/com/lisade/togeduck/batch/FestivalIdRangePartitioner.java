package com.lisade.togeduck.batch;

import com.lisade.togeduck.repository.FestivalRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

@RequiredArgsConstructor
public class FestivalIdRangePartitioner implements Partitioner {

    private final FestivalRepository festivalRepository;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Long max = festivalRepository.findMaxId();
        Long min = festivalRepository.findMinId();

        long targetSize = (max - min) / gridSize + 1;
        Map<String, ExecutionContext> result = new HashMap<>(gridSize);
        long number = 0L;
        long start = min;
        long end = start + targetSize - 1;
        while (start <= max) {
            ExecutionContext ctx = new ExecutionContext();
            result.put("partition" + number, ctx);
            if (end >= max) {
                end = max;
            }
            ctx.putLong("minId", start);
            ctx.putLong("maxId", end);

            start += targetSize;
            end += targetSize;
            number++;
        }
        return result;
    }
}
