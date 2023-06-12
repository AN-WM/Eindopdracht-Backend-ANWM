package nl.novi.EindopdrachtBackend.security;

import nl.novi.EindopdrachtBackend.filter.JwtRequestFilter;
import nl.novi.EindopdrachtBackend.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST,"/users/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST,"/customers").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST,"/earpieces").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST,"/hearingaids").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST,"/receipts").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/customers/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/earpieces/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/hearingaids/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/receipts/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.GET,"/customers").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET,"/earpieces").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET,"/hearingaids").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET,"/receipts").hasAuthority("USER")

                .requestMatchers(HttpMethod.GET,"/customers/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET,"/earpieces/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET,"/hearingaids/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET,"/receipts/**").hasAuthority("USER")

                .requestMatchers(HttpMethod.PUT,"/customers/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT,"/earpieces/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT,"/hearingaids/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT,"/receipts/**").hasAuthority("USER")

                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}