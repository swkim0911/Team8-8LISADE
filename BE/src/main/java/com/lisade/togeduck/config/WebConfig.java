package com.lisade.togeduck.config;

import com.lisade.togeduck.resolver.LoginArgumentResolver;
import com.lisade.togeduck.util.FestivalStatusRequestConverter;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new FestivalStatusRequestConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(
            httpMessageConverter -> httpMessageConverter instanceof MappingJackson2HttpMessageConverter);
        converters.add(1, new MappingJackson2HttpMessageConverter());
    }
}
