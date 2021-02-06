package es.neifi.Ridam.security.jwt.config;

import es.neifi.Ridam.security.jwt.JwtAuthenticationEntryPoint;
import es.neifi.Ridam.security.jwt.JwtAuthorizationFilter;
import es.neifi.Ridam.users.services.CustomUsersDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtAuthorizationFilter filter;
    private final CustomUsersDetailsService customUsersDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUsersDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Bean()
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/**")
                .permitAll().antMatchers(HttpMethod.GET, "/").permitAll();

                //.antMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "USER").anyRequest().authenticated();
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    }
}
