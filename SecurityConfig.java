package org.mars.demookta.springmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/myapp/public", "/myapp/api/resource").permitAll() // Public endpoints don't require authentication
                .anyRequest().authenticated()         // All other requests need authentication
                .and()
                .oauth2Login()
               // .loginPage("/oauth2/authorization/okta") // Explicitly define Okta login page// Enables OAuth2 login via Okta
                .and()
                .oauth2ResourceServer()
                .jwt();
        http.csrf().disable();
        // Validates incoming JWT tokens
        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration oktaClient = ClientRegistration.withRegistrationId("okta")
                .clientId("0oakjsb3azHK6Y9Q95d7")
                .clientSecret("mijMy5NyF4xzFa2XqwcVLnqvlUSmTxEBvpn1Kl9Qk15OcVcOU8tnEmFrmQ6ZVC01")
                .scope("openid", "profile", "email")
                .authorizationUri("https://dev-58281825.okta.com/oauth2/default/v1/authorize")
                .tokenUri("https://dev-58281825.okta.com/oauth2/default/v1/token")
                .userInfoUri("https://dev-58281825.okta.com/oauth2/default/v1/userinfo")
                .userNameAttributeName("preferred_username") // or "sub"
                //.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .redirectUri("http://localhost:8080/myapp/login/oauth2/code/okta")
                //.redirectUri("http://localhost:8080/myapp/authorization-code/callback")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .jwkSetUri("https://dev-58281825.okta.com/oauth2/default/v1/keys")
                .build();

        return new InMemoryClientRegistrationRepository(Arrays.asList(oktaClient));
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = "https://dev-58281825.okta.com/oauth2/default/v1/keys"; // Replace with your JWKS URI
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}
