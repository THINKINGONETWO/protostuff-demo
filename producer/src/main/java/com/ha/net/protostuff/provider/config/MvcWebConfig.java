package com.ha.net.protostuff.provider.config;

import com.ha.net.common.converter.ProtostuffHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.http.converter.HttpMessageConverter;
import java.util.List;

/**
 * @author jiangwentao
 */
@Configuration
@EnableWebMvc
public class MvcWebConfig extends WebMvcConfigurerAdapter{

    /**
     * 添加自定义的httpMessageConverter
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(protostuffHttpMessageConverter());
    }

    @Bean
    public ProtostuffHttpMessageConverter protostuffHttpMessageConverter(){
        return new ProtostuffHttpMessageConverter();
    }
}
