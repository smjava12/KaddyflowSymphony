package com.labs.kaddy.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableEurekaClient
@EntityScan( basePackages = {"com.labs.kaddy.flow.system.entity"})
@EnableCaching
public class KaddyflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaddyflowApplication.class, args);
	}
//	
//	@Bean
//	@LoadBalanced
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
	
//	 	@Bean
//	    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//	    public Logger getLogger(InjectionPoint injectionPoint){
//	        return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
//	    }

}
