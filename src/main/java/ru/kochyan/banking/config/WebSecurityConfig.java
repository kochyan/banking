package ru.kochyan.banking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.InvalidSessionStrategy;
import ru.kochyan.banking.config.strategies.InvalidSessionStrategyImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String prefix;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(@Value("${api.prefix}") String prefix,
                             UserDetailsService userDetailsService) {
        this.prefix = prefix;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .and()
                .invalidSessionStrategy(invalidSessionStrategy())
                .and()
                .authorizeHttpRequests()
                .antMatchers( prefix + "/auth/*").permitAll()
                .antMatchers(HttpMethod.GET, prefix + "/legal-entity/eager").hasAuthority("CAN_SELECT")
                .antMatchers(HttpMethod.PUT, prefix + "/legal-entity").hasAuthority("CAN_UPDATE")
                .antMatchers(HttpMethod.POST, prefix + "/legal-entity").hasAuthority("CAN_INSERT")
                .antMatchers(HttpMethod.DELETE, prefix + "/legal-entity").hasAuthority("CAN_DELETE")
                .antMatchers(HttpMethod.GET, prefix + "/log").hasAuthority("CAN_USE_ADMIN_PANEL")
                .anyRequest().authenticated()
                .and()
                .rememberMe().alwaysRemember(true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); //TODO
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new InvalidSessionStrategyImpl();
    }
}