package com.abhsy.easy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.abhsy.easy.feign.ISecurityServiceBiz;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${bmp.security.host}")
    private String securityHost;
    @Value("${bmp.security.port}")
    private String securityPort;

    @Bean
    public ISecurityServiceBiz loginInvoke() {
        return Feign.builder().contract(new JAXRSContract()).encoder(new JacksonEncoder()).requestInterceptor(new RequestInterceptor() {
			
			@Override
			public void apply(RequestTemplate template) 
			{
				template.header("Host", securityHost);
			}
		})
                .decoder(new JacksonDecoder()).target(ISecurityServiceBiz.class, "http://" + securityHost + ":" + securityPort + "/auth");
    }

    @Bean
    public ThreadLocal<Result> threadLocal(){
        return new ThreadLocal<Result>();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}





















