package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.GruposModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.algaworks.algafood.api.v2.openapi.model.CozinhasModelV2OpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableOpenApi
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {
	/*
	  endpoint /v2/api-docs tem um json com a defini????o gigante da api que ?? convertida pela Swagger para Html
	 */
	
	
	@Bean
	public Docket apiDocketV1() {
		
		
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.groupName("V1")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
					.paths(PathSelectors.ant("/v1/**"))
					//.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())  - deprecated
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
//				.globalRequestParameters(Collections.singletonList(
//					      new RequestParameterBuilder()
//					          .name("campos")
//					          .description("Nomes das propriedades para filtrar na resposta, separados por v??rgula")
//					          .in(ParameterType.QUERY)
//					          .required(false)
//					          .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//					          .build()))
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class) // , URI.class (java.net), URL.class (java.net), URLStreamHandler.class (java.net), Resource.class (spring core io), File.class (java.io), InputStream (java.io)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaModel.class),
						CozinhasModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
						PedidosResumoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModel.class),
						CidadesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
				        typeResolver.resolve(CollectionModel.class, EstadoModel.class),
				        EstadosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
					    FormasPagamentoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, GrupoModel.class),
					    GruposModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
				        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
				        PermissoesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
					    ProdutosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
					    RestaurantesBasicoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
				        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
				        UsuariosModelOpenApi.class))
				
//				.securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(List.of(authenticationScheme()))
				.securityContexts(List.of(securityContext()))
				
				.apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos de usu??rios"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes"),
						new Tag("Usu??rios", "Gerencia os usu??rios"),
						new Tag("Estat??sticas", "Estat??sticas da AlgaFood"),
						new Tag("Permiss??es", "Gerencia as permiss??es"));
				
	}
	
//	private SecurityScheme securityScheme() {
//		return new OAuthBuilder()
//				.name("AlgaFood")
//				.grantTypes(grantTypes())
//				.scopes(scopes())
//				.build();
//	}

	
	
//	private List<GrantType> grantTypes() {
//	return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
//}

	
	private List<AuthorizationScope> scopes() {
		return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"),
				new AuthorizationScope("WRITE", "Acesso de escrita"));
	}
	
	
//	
//	private SecurityContext securityContext() {
//		var securityReference = SecurityReference.builder()
//				.reference("AlgaFood")
//				.scopes(scopes().toArray(new AuthorizationScope[0]))
//				.build();
//		
//	
//		return SecurityContext.builder()
//				.securityReferences(Arrays.asList(securityReference))
////				.forPaths(PathSelectors.any())
//				.operationSelector(operationContext -> true)
//				.build();
//	}
	
	
	private SecurityContext securityContext() {		
		return SecurityContext.builder()
				.securityReferences(securityReference()).build();
	}
	
	private List<SecurityReference> securityReference() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Authorization", authorizationScopes));
	}
	
	private HttpAuthenticationScheme authenticationScheme() {
		return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
	}
	
	
//	@Bean
	public Docket apiDocketV2() {
		
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.groupName("V2")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
					.paths(PathSelectors.ant("/v2/**"))
				.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.apiInfo(apiInfoV2())
				
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
					    CozinhasModelV2OpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
					        CidadesModelV2OpenApi.class))
					.apiInfo(apiInfoV2())
					        
					.tags(new Tag("Cidades", "Gerencia as cidades"),
					        new Tag("Cozinhas", "Gerencia as cozinhas"));
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
						.description("Recurso n??o possui representa????o que poderia ser aceita pelo consumidor")
						.build()		
				);
	}
	
	private List<Response> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code("400")
					.description("Requisi????o Inv??lida (erro do cliente)") // Bad Request
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblema())
					.build(),	
				new ResponseBuilder()
					.code("406") // Not Acceptable
					.description("Recurso n??o possui representa????o que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseBuilder()
					.code("415") // Unsupported Media Type
					.description("Requisi????o recusada porque o corpo est?? em um formato n??o suportado")
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
					.description("Requisi????o Inv??lida (erro do cliente)") // Bad Request
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
	

	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("Algafood Api")
				.description("API Aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Algaworks", "https://www.algaworks/com", "contato@algaworks.com"))
				.build();
	}
	
	private ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("Algafood API (Depreciada)")
				.description("API Aberta para clientes e restaurantes.<br><br>"
						+ "<strong>Essa vers??o da API est?? depreciada e deixar?? de existir a partir de 01/01/2025. "
						+ "Use a vers??o mais atual da API.</strong>")
				.version("2")
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
//		necess??rio apenas para Swagger2
			
	}
	
}
