package com.ha.net.protostuff.consumer.config;

import com.ha.net.common.converter.ProtostuffHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
@Configuration
@EnableWebMvc
public class ConsumerWebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ProtostuffHttpMessageConverter protostuffHttpMessageConverter;

    /**
     * 添加自定义的httpMessageConverter
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(protostuffHttpMessageConverter);
    }
}
