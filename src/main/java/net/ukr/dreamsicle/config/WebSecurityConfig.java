package net.ukr.dreamsicle.config;

import lombok.AllArgsConstructor;
import lombok.Lombok;
import net.ukr.dreamsicle.model.user.RoleType;
import net.ukr.dreamsicle.security.JwtUserDetailsService;
import net.ukr.dreamsicle.security.jwt.AuthEntryPoint;
import net.ukr.dreamsicle.security.jwt.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for JWT based Spring Security application.
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtUserDetailsService userDetailsService;
    private final AuthEntryPoint unauthorizedHandler;
    private final ApplicationConfig applicationConfig;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(applicationConfig.passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST, "/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/password/**").hasAuthority(RoleType.ADMIN.getName())
                .antMatchers(HttpMethod.PUT, "/users/password/**").hasAuthority(RoleType.USER.getName())
                .antMatchers(HttpMethod.POST, "/user/id/details/**").hasAuthority(RoleType.USER.getName())
                .antMatchers(HttpMethod.POST, "/user/id/details/**").hasAuthority(RoleType.ADMIN.getName())
                .antMatchers("/**").hasAuthority(RoleType.ADMIN.getName())
                .antMatchers("/users/**").hasAuthority(RoleType.ADMIN.getName())
                .antMatchers("/currencies/**").hasAuthority(RoleType.ADMIN.getName())
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
