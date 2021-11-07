package com.algaworks.algafood.core.security.authorizationserver;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/*
 	https://gist.github.com/thiagofa/ef9a40d495016cb2581add41b5cbde1b
	Spring Security OAuth2 com Java 11+ lança exception:
	Caused by: java.lang.ClassNotFoundException: javax.xml.bind.JAXBException
	Em um projeto com Spring Boot, adicione as dependências ao lado e abaixo para resolver o problema: : jaxb-api, jaxb-core e jaxbl-impl
	Leia também: https://stackoverflow.com/questions/52502189/java-11-package-javax-xml-bind-does-not-exist
	
	<dependency>
		<groupId>javax.xml.bind</groupId>
		<artifactId>jaxb-api</artifactId>
	</dependency>
	<dependency>
		<groupId>com.sun.xml.bind</groupId>
		<artifactId>jaxb-core</artifactId>
		<version>2.3.0.1</version>
	</dependency>
	<dependency>
		<groupId>com.sun.xml.bind</groupId>
		<artifactId>jaxb-impl</artifactId>
		<version>${javax-jaxb.version}</version>
	</dependency>
	
*/


@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;
	
	@Autowired
	private DataSource dataSource;
	
//	@Autowired
//	private RedisConnectionFactory redisConnectionFactory;   PARA SALVAR TOKEN NO REDIS
	
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory()
//			.withClient("algafood-web")
//				.secret(passwordEncoder.encode("123"))
//				.authorizedGrantTypes("password", "refresh_token")
//				.scopes("WRITE", "READ")
//				.accessTokenValiditySeconds(6 * 60 * 60) // 6horas: 6 * 60 * 60 (padrão é 12 horas) . 
//				.refreshTokenValiditySeconds(60 * 24 * 60 * 60) // para 60 dias? 60 dias * 24 horas * 60 minutos * 60 segundos
//				
//			.and()
//				.withClient("foodanalytics")
//				.secret(passwordEncoder.encode("123"))
//				.authorizedGrantTypes("authorization_code")
//				.scopes("WRITE", "READ")
//				.redirectUris("http://www.foodanalytics.local:8082")		
				// http://auth.algafood.local:8081/oauth/authorize?response_type=code&client_id=foodanalytics&state=abc&redirect_uri=http://aplicacao-cliente
				
				//http://auth.algafood.local:8081/oauth/authorize?response_type=code&client_id=foodanalytics&redirect_uri=http://www.foodanalytics.local:8082&state=abc
				
				/*
				 * 
				 * http://auth.algafood.local:8081/oauth/authorize?response_type=code&client_id=foodanalytics&state=abc&redirect_uri=http://www.foodanalytics.local:8082



					http://auth.algafood.local:8081/oauth/authorize?response_type=token&client_id=webadmin&state=abc&redirect_uri=http://aplicacao-cliente


					http://aplicacao-cliente/#access_token=Nj4tCbh2ks6JsVzRvQfZU4oGJf4&token_type=bearer&state=abc&expires_in=43199&scope=read%20write
					
					
					http://auth.algafood.local:8081/oauth/authorize?response_type=code&client_id=foodanalytics&redirect_uri=http://www.foodanalytics.local:8082&code_challenge=teste&code_challenge_method=plain

					Code Challenge: teste123   (method plain)
					Code Verifier: teste123
					
					http://auth.algafood.local:8081/oauth/authorize?response_type=code&client_id=foodanalytics&redirect_uri=http://www.foodanalytics.local:8082&code_challenge=POSxFwrNhqYyL2GAkyR8Xf1SWcwNKyzibAY1oPAKZuE&code_challenge_method=s256

					code verifier: kSZsOgyKxnEK0zsdawLFv6GsHCL6tJLKzx00vrv4NXow--JTFCIR4f0E9f8~UB8HijMNcAv0mWDOBTp4GDoiPjmZn5jk02YzPZWmeTbs6zzh45iYU70yVqxYrHbCYvym
				
					code challenge: POSxFwrNhqYyL2GAkyR8Xf1SWcwNKyzibAY1oPAKZuE
					
					https://tonyxu-io.github.io/pkce-generator/

				 */
					
//			.and()
//				.withClient("webadmin")
//				.authorizedGrantTypes("implicit")
//				.scopes("WRITE", "READ")
//				.redirectUris("http://aplicacao-cliente")
//				
//				
//			.and()
//				.withClient("faturamento")
//				.secret(passwordEncoder.encode("123"))
//				.authorizedGrantTypes("client_credentials")
//				.scopes("READ")
//						
//			.and()
//				.withClient("checktoken")
//					.secret(passwordEncoder.encode("check123"));
//
//	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.checkTokenAccess("isAuthenticated()");
		security.checkTokenAccess("permitAll()")
			.tokenKeyAccess("permitAll()");
//			.allowFormAuthenticationForClients();

	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		var enhancerChain = new TokenEnhancerChain();
		
		enhancerChain.setTokenEnhancers(
				Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));
		
		endpoints
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.reuseRefreshTokens(false)
//				.tokenStore(redisTokenSstore())     *** PARA SALVAR TOKEN NO REDIS
				.accessTokenConverter(jwtAccessTokenConverter())
				.tokenEnhancer(enhancerChain)
				.approvalStore(approvalStore(endpoints.getTokenStore()))
				.tokenGranter(tokenGranter(endpoints)); // add suporte a PKCE
	}
	
	private ApprovalStore approvalStore(TokenStore tokenStore) {
		var approvalStore = new TokenApprovalStore();
		approvalStore.setTokenStore(tokenStore);
		
		return approvalStore;
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		var jwtAccessTokenConverter = new JwtAccessTokenConverter();
		
//		jwtAccessTokenConverter.setSigningKey("ladkfjkakflndafadjksfhkajshfjkah981y9138y918yjdashfklh19873"); // se o secret for menor que 32bytes dará erro 401 em uma requisição no Resource Server. O log da aplicação não mostrará isso, somente se ativar o log por debbuging. Para evitar isso, usar uma chave com mais de 32bytes.
		
/*
	Gerando um arquivo JKS com um par de chaves
	
	keytool -genkeypair -alias algafood -keyalg RSA -keypass 123456 -keystore algafood.jks -storepass 123456 -validity 3650
	
	Listando as entradas de um arquivo JKS
	
	keytool -list -keystore algafood.jks
	
	**************************************************** 
	
	Gerando o certificado
	
	keytool -export -rfc -alias algafood -keystore algafood.jks -file algafood-cert.pem
	
	Gerando a chave pública
	
	openssl x509 -pubkey -noout -in algafood-cert.pem > algafood-pkey.pem

 */	
		var keyStorePass = jwtKeyStoreProperties.getPassword();
		var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();
		
		var keyStoreKeyFactory = new KeyStoreKeyFactory(
				jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray()); //abrir o arquivo JKS 
		var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);
		
		jwtAccessTokenConverter.setKeyPair(keyPair);
		
		
		return jwtAccessTokenConverter;
	}
	

/*
  para salvar token no redis:
  
  private TokenStore redisTokenSstore() {
		return new RedisTokenStore(redisConnectionFactory);
	}
	
 */
	
	
	//https://gist.github.com/thiagofa/daca4f4790b5b18fed800b83747127ca referencia -- ADD suporte a PKCE mais a classe PkceAuthorizationCodeTokeGranter
	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
}
