package com.labs.kaddy.flow.infra.config.apidoc;

import java.time.YearMonth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicate;

import lombok.AllArgsConstructor;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableConfigurationProperties(Swagger2Properties.class)
@ConditionalOnProperty(prefix= "application", name = "swagger2.enabled")
@EnableSwagger2
@AllArgsConstructor
public class SwaggerConfig {   
	private final Swagger2Properties properties;
	
    @Bean
    public Docket swaggerSpringMvcPlugin() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))              
          .paths(PathSelectors.any())                          
          .build()
          .directModelSubstitute(YearMonth.class, String.class).apiInfo(apiInfo());                                           
    }
    
    private Predicate<String> paths() {
    	return PathSelectors.regex(properties.getPathPattern());
    }
    
    @SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
    	return new ApiInfo(properties.getTitle(),properties.getDescription(),properties.getVersion(),
    			properties.getTermOfServiceUrl(),"",
    			properties.getLicense(),properties.getLicenseUrl());
    }
    
  
}
