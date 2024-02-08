package com.lisade.togeduck.util;

import com.lisade.togeduck.entity.Status;
import org.springframework.core.convert.converter.Converter;

public class StatusRequestConverter implements Converter<String, Status> {

    @Override
    public Status convert(String code) {
        return Status.of(code);
    }
}
