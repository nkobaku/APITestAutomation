package com.devopslabs.apitestautomation.swagger;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
@Slf4j
public class SwaggerConfig {

  @Bean
  public Docket api() {
    log.info("Starting Swagger...");
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  /**
   *
   * Create the basic information of the API
   */
  private ApiInfo getApiInfo() {
    return new ApiInfo(
        "DevOpsLabs REST APIs Documentation",
        "DevOpsLabs REST API Documentation",
        "1.0",
        "Terms Of Service",
        new Contact("Niranjan Kobaku", "", "niranjan.kobaku@gmail.com"),
        "https://github.com/nkobaku",
        "https://github.com/nkobaku",
        new ArrayList<VendorExtension>());
  }
}
