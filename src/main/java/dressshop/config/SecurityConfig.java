package dressshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/orders/**").hasAnyRole("USER")
                    .anyRequest().permitAll();
        });

        /*http.formLogin(f -> {
            f.loginPage("/login");
            f.loginProcessingUrl("/loginProc");
            f.defaultSuccessUrl("/");
        });*/

        return http.build();
    }
}
