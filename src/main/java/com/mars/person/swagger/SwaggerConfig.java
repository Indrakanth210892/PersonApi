package com.mars.person.swagger;

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


@Configuration
@EnableSwagger2
public class SwaggerConfig{

	@Bean
	public Docket swaggerDocket() {
		
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.mars.person.controller"))
				.paths(PathSelectors.regex("/person.*"))
				.build()
				.apiInfo(apiInfo());
	
	
	}

	//These details can be externalized to a property/yml file. But for the sake of time hardcoded here itself.
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Person API")
				.description("RESTFUL service for CRUD Operations on Person records")
				.contact(new Contact("Priya Immandi", "www.mars-india.com", "Priya.Immandi@mars-india.com"))
				.license("MARS-INDIA-PVT Licensed")
				.licenseUrl("https://www.mars-india.com/contact-us.html")
				.version("1.0.0")
				.build();
	}
	


}
