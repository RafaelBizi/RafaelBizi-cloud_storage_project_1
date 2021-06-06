package com.example.demo.config;

import com.example.demo.service.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
            .antMatchers("/signup","/h2-console/**","/css/**","/js/**").permitAll()
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/login")
            .permitAll();

        http.formLogin()
            .defaultSuccessUrl("/home", true);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
