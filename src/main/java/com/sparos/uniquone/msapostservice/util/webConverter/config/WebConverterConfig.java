package com.sparos.uniquone.msapostservice.util.webConverter.config;

import com.sparos.uniquone.msapostservice.util.webConverter.converter.AdminBoardTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConverterConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AdminBoardTypeConverter());
    }
}
