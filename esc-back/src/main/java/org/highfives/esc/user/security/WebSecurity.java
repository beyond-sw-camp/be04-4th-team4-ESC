package org.highfives.esc.user.security;

import org.highfives.esc.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    private UserService userService;
    private Environment env;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private JwtUtil jwtUtil;

    public WebSecurity(UserService userService,
                       Environment env,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       JwtUtil jwtUtil) {
        this.userService = userService;
        this.env = env;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        /* 설명. 로그인 시 추가 */
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf((csrf) -> csrf.disable());

//        http.authorizeHttpRequests((authz) ->
//                        authz.requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll());

        /* 설명. /user-service/users/** 패턴을 제외하고는 특정 IP 주소에서 온 요청에만 접근을 허용하며, 그 외의 모든 요청은 인증된 사용자에게만 허용한다는 것을 보여준다. */
        http.authorizeHttpRequests((authz) -> authz
                                .requestMatchers(new AntPathRequestMatcher("/**", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/**", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/**", "PUT")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/**", "DELETE")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/metrics")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/actuator/prometheus")).permitAll()
                                .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilter(getAuthenticationFilter(authenticationManager));
        http.addFilterBefore(new JwtFilter(userService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        return new AuthenticationFilter(authenticationManager, userService, env);
    }


}
