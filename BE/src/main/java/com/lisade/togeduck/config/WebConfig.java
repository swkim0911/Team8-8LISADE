package com.lisade.togeduck.config;

import com.lisade.togeduck.util.CategoryRequestConverter;
import com.lisade.togeduck.util.FestivalStatusRequestConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CategoryRequestConverter());
        registry.addConverter(new FestivalStatusRequestConverter());
    }
}
