package org.highfives.esc.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.service.UserService;
import org.highfives.esc.user.vo.RequestLogin;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {

            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        System.out.println("시크릿 키: " + environment.getProperty("token.secret"));
        String userName = ((User)auth.getPrincipal()).getUsername();
        UserDTO userDetails = userService.getUserDetailsByEmail(userName);

//        String token = Jwts.builder()
//                .setSubject(userDetails.getEmail())
//                .setExpiration(new Date(System.currentTimeMillis() +
//                        Long.parseLong(environment.getProperty("token.expiration_time"))))
//                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
//                .compact();

        /* 설명. Authentication에서부터 security에 의해 관리되는 회원의 권한을 추출하고 List로 만듦 */
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_ENTERPRISE");

        Claims claims = Jwts.claims().setSubject(userDetails.getEmail());
        claims.put("auth", roles);

        String token = Jwts.builder()
                .setClaims(claims)
//                .claim("userEmail", userDetails.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getEmail());
    }
}
