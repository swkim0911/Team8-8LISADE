package com.lisade.togeduck.util;

import com.lisade.togeduck.entity.enums.FestivalStatus;
import org.springframework.core.convert.converter.Converter;

public class FestivalStatusRequestConverter implements Converter<String, FestivalStatus> {

    @Override
    public FestivalStatus convert(String code) {
        return FestivalStatus.of(code);
    }
}
