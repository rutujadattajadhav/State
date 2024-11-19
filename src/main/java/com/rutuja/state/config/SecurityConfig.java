package com.rutuja.state.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    //private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.cors(corsSpec -> corsSpec.disable())
                .csrf(csrfSpec -> csrfSpec.disable());
                //.httpBasic(Customizer.withDefaults())
                //.authorizeExchange(exchanges -> exchanges
                        //.pathMatchers( "/saveUser").permitAll()
                        //.anyExchange().authenticated());
                //.addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
