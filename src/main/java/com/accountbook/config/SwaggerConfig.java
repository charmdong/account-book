package com.accountbook.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {
    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                    .title("account book API")
                    .description("account book API Docs")
                    .version("1.0.0")
                    .build();
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                // .consumes(getConsumeContentTypes())
                // .produces(getProduceContentTypes())
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.accountbook.api"))
                .paths(PathSelectors.any())
                .build();
    }

    // private Set<String> getConsumeContentTypes() {
    //     Set<String> consumes = new HashSet<>();
    //     consumes.add("application/json;charset=UTF-8");
    //     return consumes;
    // }

    // private Set<String> getProduceContentTypes() {
    //     Set<String> produces = new HashSet<>();
    //     produces.add("application/json;charset=UTF-8");
    //     return produces;
    // }
}