package com.livngroup.gds.config;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.livngroup.gds.web")
public class ApiDocConfiguration {
    
	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any())
				.paths(paths()).build().pathMapping("/")
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(newRule(typeResolver.resolve(DeferredResult.class, 
												typeResolver.resolve(ResponseEntity.class, WildcardType.class)), 
													typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, responseMessages())
				.globalResponseMessage(RequestMethod.POST, responseMessages())
				.apiInfo(apiInfo())
				.securitySchemes(newArrayList(apiKey()))
				.securityContexts(newArrayList(securityContext()));
	}
 
	@Autowired
	private TypeResolver typeResolver;
	
	@SuppressWarnings("unchecked")
	private Predicate<String> paths() {
		return or(regex("/backupcard.*"), regex("/purchase.*"), regex("/payment.*"));
	}

	private List<ResponseMessage> responseMessages() {
		ResponseMessage notProperRequestMessage = new ResponseMessageBuilder()
												.code(400).message("Not proper request")
												.responseModel(new ModelRef("ErrorResponse")).build();
		ResponseMessage notAuthorisedRequestMessage = new ResponseMessageBuilder()
												.code(401).message("Not authorised request")
												.responseModel(new ModelRef("ErrorResponse")).build();
		
		return newArrayList(notAuthorisedRequestMessage, notProperRequestMessage);
	}
	
	private ApiKey apiKey() {
		return new ApiKey("mykey", "api_key", "header");
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo("WEX-GDS Interface", "Interface Api Documentation", "1.0", "urn:tos",
		          "Contact Email", "Document License", "http://www.livngroup.com/");
	}
 
	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/anyPath.*")).build();
	}
 
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(new SecurityReference("mykey", authorizationScopes));
	}
 
	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration("test-app-client-id", "test-app-realm", "test-app", "apiKey");
	}
 
	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl");
	}

}
