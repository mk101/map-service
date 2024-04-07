package kolesov.maksim.mapping.map.config;

import jakarta.servlet.http.HttpServletResponse;
import kolesov.maksim.mapping.map.controller.LayerController;
import kolesov.maksim.mapping.map.controller.TagController;
import kolesov.maksim.mapping.map.filter.JwtAuthenticationManager;
import kolesov.maksim.mapping.map.filter.JwtFilter;
import kolesov.maksim.mapping.map.service.JwtService;
import kolesov.maksim.mapping.map.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/actuator/health")
                                    .permitAll()
                                .requestMatchers(HttpMethod.GET, "/layers")
                                    .permitAll()
                                .requestMatchers("/search")
                                    .permitAll()
                                .anyRequest()
                                    .authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()))
                        .accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage()))
                )
                .addFilter(jwtFilter);

        return http.build();
    }

    @Bean
    public JwtFilter jwtFilter(
            JwtAuthenticationManager authenticationManager,
            JwtService jwtService,
            UserService userService
    ) {
        JwtFilter filter = new JwtFilter(jwtService, userService);
        filter.setAuthenticationManager(authenticationManager);
        filter.initPaths(List.of(LayerController.class, TagController.class));

        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
