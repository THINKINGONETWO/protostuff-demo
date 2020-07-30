package com.ha.net.protostuff.consumer.config;

import com.ha.net.common.converter.ProtostuffHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;

/**
 * @author jiangwentao
 */
@Configuration
public class CustomFeignClientConfiguration extends FeignClientsConfiguration {

    @Bean
    public ProtostuffHttpMessageConverter protostuffHttpMessageConverter() {
        return new ProtostuffHttpMessageConverter();
    }
}
