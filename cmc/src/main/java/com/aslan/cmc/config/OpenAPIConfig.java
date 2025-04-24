package com.aslan.cmc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Commerce API")
                        .version("1.0.0")
                        .description("상품 조회, 검색, 주문 및 결제 기능을 제공하는 API"));
    }
}