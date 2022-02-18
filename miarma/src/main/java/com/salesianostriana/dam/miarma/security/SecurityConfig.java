package com.salesianostriana.dam.miarma.security;


import com.salesianostriana.dam.miarma.security.jwt.JwtAccessDeniedHandler;
import com.salesianostriana.dam.miarma.security.jwt.JwtAuthorizationFilter;;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthorizationFilter filter;
    private final JwtAccessDeniedHandler accessDeniedHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()

                //REGISTRO/LOGIN

                    .antMatchers(HttpMethod.POST, "/auth/register").anonymous()
                    .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .antMatchers(HttpMethod.GET, "/me").authenticated()
                    .antMatchers(HttpMethod.GET,"/download/{filename:.+}").permitAll()

                //PUBLICACIONES
                .antMatchers(HttpMethod.POST, "/post/").authenticated()
                .antMatchers(HttpMethod.GET,"/post/public").authenticated()
                .antMatchers(HttpMethod.GET,"/post/").authenticated()
                .antMatchers(HttpMethod.GET,"/post/public").authenticated()
                .antMatchers(HttpMethod.GET,"/post/me").authenticated()
                .antMatchers(HttpMethod.DELETE, "/post/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/post/{id}").authenticated()

                //USUARIOS
                .antMatchers(HttpMethod.GET, "/profile/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/profile/**").authenticated()

                    .antMatchers("/h2-console/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    .anyRequest().authenticated();


        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        // Para dar acceso a h2
        http.headers().frameOptions().disable();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
