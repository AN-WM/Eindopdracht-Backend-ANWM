package com.example.HwLes11ANWM.security;


import com.example.HwLes11ANWM.filter.JwtRequestFilter;
import com.example.HwLes11ANWM.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;



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

        //JWT token authentication
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET,"/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST,"/cimodules").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/remotecontrollers").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/televisions").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/wallbrackets").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.DELETE, "/cimodules/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/remotecontrollers/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/televisions/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/wallbrackets/**").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET,"/cimodules").hasAuthority("USER")
                .antMatchers(HttpMethod.GET,"/remotecontrollers").hasAuthority("USER")
                .antMatchers(HttpMethod.GET,"/televisions").hasAuthority("USER")
                .antMatchers(HttpMethod.GET,"/wallbrackets").hasAuthority("USER")

                .antMatchers(HttpMethod.GET,"/cimodules/**").hasAuthority("USER")
                .antMatchers(HttpMethod.GET,"/remotecontrollers/**").hasAuthority("USER")
                .antMatchers(HttpMethod.GET,"/televisions/**").hasAuthority("USER")
                .antMatchers(HttpMethod.GET,"/wallbrackets/**").hasAuthority("USER")

                .antMatchers(HttpMethod.PUT,"/cimodules/**").hasAuthority("USER")
                .antMatchers(HttpMethod.PUT,"/remotecontrollers/**").hasAuthority("USER")
                .antMatchers(HttpMethod.PUT,"/televisions/**").hasAuthority("USER")
                .antMatchers(HttpMethod.PUT,"/wallbrackets/**").hasAuthority("USER")

                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()/*allen dit punt mag toegankelijk zijn voor niet ingelogde gebruikers*/
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}