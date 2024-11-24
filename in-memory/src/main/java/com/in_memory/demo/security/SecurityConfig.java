package com.in_memory.demo.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
   public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("furkan")
                .password(bCryptPasswordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user123")
                .password(bCryptPasswordEncoder().encode("password123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSession httpSession) throws Exception {
        http
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)//default formu kapatır işlemleri header üzerinden sağlarız
                .authorizeHttpRequests(x -> x.requestMatchers("/public/**","/auth/**").permitAll())
                .authorizeHttpRequests(x -> x.requestMatchers("/private/admin/**").hasRole("ADMIN"))
                .authorizeHttpRequests(x -> x.requestMatchers("/private/user/**").hasRole("USER"))
                .authorizeHttpRequests(x -> x.anyRequest().authenticated()) // Burayı en üste yazarsak eğer - diğer alt işlemler için giriş yapmakz zorunda kalırız
                .httpBasic(Customizer.withDefaults());

        return http.build();

    }
}
