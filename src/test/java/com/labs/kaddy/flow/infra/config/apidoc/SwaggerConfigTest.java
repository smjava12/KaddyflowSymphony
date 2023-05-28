/**
 * 
 */
package com.labs.kaddy.flow.infra.config.apidoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Surjyakanta
 *
 */
@ExtendWith(MockitoExtension.class)
public class SwaggerConfigTest {

	private Swagger2Properties swagger2Properties;
	private SwaggerConfig swaggerConfig;
	
	@BeforeEach
	public void setup() {
		swagger2Properties = new Swagger2Properties();
		swaggerConfig = new SwaggerConfig(swagger2Properties);
	}
	
	@Test
	public void testSwaggerConfig() {
		swagger2Properties.setTitle("KaddyfliowSwaggerUI");
		swagger2Properties.setDescription("KaddyflowAPI provide workflow management");
		swagger2Properties.setVersion("1.1");
		swagger2Properties.setTermOfServiceUrl("10 years");
		swagger2Properties.setLicense("Licencse 1.0");
		swagger2Properties.setLicenseUrl("https://swagger.com/licenceUrl");
		swagger2Properties.setContact("Contact");
		swagger2Properties.setEnabled(true);
		swagger2Properties.setPathPattern("docTest");
		
		Docket docket = swaggerConfig.swaggerSpringMvcPlugin();
		
		Assertions.assertNotNull(docket);
		Assertions.assertEquals("KaddyfliowSwaggerUI", swagger2Properties.getTitle());
		Assertions.assertEquals("KaddyflowAPI provide workflow management", swagger2Properties.getDescription());
		Assertions.assertEquals("1.1", swagger2Properties.getVersion()); 
		Assertions.assertEquals("10 years", swagger2Properties.getTermOfServiceUrl());
		Assertions.assertEquals("Licencse 1.0", swagger2Properties.getLicense());
		Assertions.assertEquals("https://swagger.com/licenceUrl", swagger2Properties.getLicenseUrl());
		Assertions.assertEquals("Contact", swagger2Properties.getContact());
		Assertions.assertEquals(true, swagger2Properties.getEnabled());
		Assertions.assertEquals("docTest", swagger2Properties.getPathPattern());
	}
}
