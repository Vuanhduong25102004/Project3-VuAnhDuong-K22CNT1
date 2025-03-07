package com.securityweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;

	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/","/home","/css/**").permitAll()
            	.requestMatchers("/admin/**").hasRole("ADMIN") 	
            	.requestMatchers("https://code.jquery.com https://cdn.jsdelivr.net","https://maxcdn.bootstrapcdn.com; " ,
                                       "https://code.jquery.com","https://cdn.jsdelivr.net","https://maxcdn.bootstrapcdn.com; " ,
                                       "https://cdn.jsdelivr.net","https://maxcdn.bootstrapcdn.com;").permitAll()
            	.requestMatchers("/appointments").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                    .loginPage("/login")
                    .usernameParameter("username") // Nếu sử dụng email, bạn có thể đổi thành "email"
                    .permitAll()
                )
            .logout(logout -> logout
            		 .logoutSuccessUrl("/")
            		.permitAll())
            .userDetailsService(customUserDetailsService);
            

        return http.build();
    }
	
	

}
