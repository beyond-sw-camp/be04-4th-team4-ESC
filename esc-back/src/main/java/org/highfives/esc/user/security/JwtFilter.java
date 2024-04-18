package org.highfives.esc.user.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.highfives.esc.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public JwtFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("UsernamePasswordAuthenticationFilter보다 먼저 동작하는 필터");
        System.out.println("authorizationHeader ====" + authorizationHeader);
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);

        /* 설명. JWT에 헤더가 있는 경우 */
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("토큰 값: " + token);
            if(jwtUtil.validateToken(token)) {
                Authentication authentication = jwtUtil.getAuthentication(token);
                System.out.println("JwtFilter 동작 후 관리 될 Authentication(Principal 객체): " + authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else System.out.println("헤더가 없어용");
        filterChain.doFilter(request, response);
    }
}
