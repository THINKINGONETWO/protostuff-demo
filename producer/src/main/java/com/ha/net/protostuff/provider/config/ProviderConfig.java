package com.ha.net.protostuff.provider.config;

import com.ha.net.common.converter.ProtostuffHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangwentao
 */
@Configuration
public class ProviderConfig {
    @Bean
    public ProtostuffHttpMessageConverter protostuffHttpMessageConverter(){
        return new ProtostuffHttpMessageConverter();
    }
}
