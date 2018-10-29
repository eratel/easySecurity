package com.abhsy.easy.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-10
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .pathMapping("/auth")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tg.bmp"))
                .paths(PathSelectors.any())
                .build();
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("鉴权中心")
                .description("数据中心")
                .contact(new Contact("Jikai.Sun","","sunjk@cn95598.com"))
                .version("1.0")
                .build();
    }
}
