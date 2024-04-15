package dressshop.config;

import dressshop.config.auth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers("/orders/**").authenticated()
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().permitAll();
        });

        http.formLogin(f -> {
            f.loginPage("/loginForm");
            f.loginProcessingUrl("/login");
            f.defaultSuccessUrl("/");
            f.usernameParameter("email");
        });

        http.oauth2Login(o -> {
            o.loginPage("/loginForm");
            o.defaultSuccessUrl("/");
            o.userInfoEndpoint(userInfoEndpointConfig ->
                    userInfoEndpointConfig.userService(principalOauth2UserService));
        });

        return http.build();
    }


}
