package com.securityweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.service.VadCustomUserDetailsService;

@Configuration
public class VadSecurityConfig {
	
	@Autowired
    private VadCustomUserDetailsService customUserDetailsService;
	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/","/home","/css/**","/js/**","/webfonts/**","/register").permitAll()
            	.requestMatchers("/admin/**").hasRole("ADMIN") 	
            	.requestMatchers("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css").permitAll()
            	.requestMatchers("/appointments").authenticated()
                .anyRequest().permitAll()
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 🔥 Cho phép tạo session khi cần thiết
                )
            .formLogin(login -> login
                    .loginPage("/login")
                    .usernameParameter("username") // Nếu sử dụng email, bạn có thể đổi thành "email"
                    .permitAll()
                )
            .logout(logout -> logout
            		.logoutUrl("/logout")
            		.logoutSuccessUrl("/")
            		.invalidateHttpSession(true)
            		.deleteCookies("JSESSIONID")
            		.permitAll())
            .userDetailsService(customUserDetailsService);
           	
            

        return http.build();
    }
	
	

}
