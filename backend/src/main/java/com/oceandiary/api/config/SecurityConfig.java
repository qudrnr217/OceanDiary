package com.oceandiary.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public JwtTokenAuthenticationFilter tokenAuthenticationFilter() {
//        return new JwtTokenAuthenticationFilter(tokenProvider, customUserDetailService);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors()
//                .and()
//                .headers().frameOptions().disable()
//                .and()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .rememberMe().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/docs/**").permitAll()
//                .antMatchers("/api/user/refreshtoken", "/api/user/login",
//                        "/api/posts","/api/skills").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)
//                .and()
//                .successHandler(oAuth2SuccessHandler)
//                .and()
//                .addFilterBefore(tokenAuthenticationFilter(), OAuth2LoginAuthenticationFilter.class);
//    }
//
}
