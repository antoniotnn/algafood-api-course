package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableOpenApi
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {
	/*
	  endpoint /v2/api-docs tem um json com a definição gigante da api que é convertida pela Swagger para Html
	 */
	
	
	@Bean
	public Docket apiDocket() {
		
		
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
					.paths(PathSelectors.any())
					//.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())  - deprecated
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, CozinhaModel.class),
						CozinhasModelOpenApi.class))
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos de usuários"));
				
	}
	
	private List<Response> globalGetResponseMessages() {
		return Arrays.asList(
					new ResponseBuilder()
						.code("500")
						.description("Erro interno do servidor") // Internal Server Error
						.representation(MediaType.APPLICATION_JSON)
						.apply(builderModelProblema())
						.build(),	
					new ResponseBuilder()
						.code("406") // Not Acceptable
						.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
						.build()		
				);
	}
	
	private List<Response> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code("400")
					.description("Requisição Inválida (erro do cliente)") // Bad Request
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblema())
					.build(),	
				new ResponseBuilder()
					.code("406") // Not Acceptable
					.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseBuilder()
					.code("415") // Unsupported Media Type
					.description("Requisição recusada porque o corpo está em um formato não suportado")
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblema())
					.build(),
				new ResponseBuilder()
					.code("500")
					.description("Erro interno do servidor") // Internal Server Error
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblema())
					.build()
			);
	}
	
	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code("400")
					.description("Requisição Inválida (erro do cliente)") // Bad Request
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblema())
					.build(),
				new ResponseBuilder()
					.code("500")
					.description("Erro interno do servidor") // Internal Server Error
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblema())
					.build()			
				);
	}
	
	private Consumer<RepresentationBuilder> builderModelProblema() {
		return r->r.model(m->m.name("Problema")
				.referenceModel(
						ref->ref.key(
								k->k.qualifiedModelName(
										q->q.name("Problema")
										.namespace("com.algaworks.algafood.api.exceptionhandler")
										))));
		
	}
	

	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Algafood Api")
				.description("API Aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Algaworks", "https://www.algaworks/com", "contato@algaworks.com"))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("index.html")
			.addResourceLocations("classpath:/META-INF/resources/");	
//		registry.addResourceHandler("/webjars/**")
//			.addResourceLocations("classpath:/META-INF/resources/webjars/");
//		
//		necessário apenas para Swagger2
			
	}
	
}
