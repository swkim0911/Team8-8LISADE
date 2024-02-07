package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.FestivalDto;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.repository.FestivalRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public Page<FestivalDto> getList(Pageable pageable, Long category, String filterType) {
        return null;
    }

    public Festival get(Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        return optionalFestival.orElse(null);
    }
}
