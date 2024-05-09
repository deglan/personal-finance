package com.example.finance.context;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.finance.auth.ClaimsStrategy;
import com.example.finance.auth.TokenService;
import com.example.finance.model.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Configuration
@AllArgsConstructor
public class UserContextConfiguration {

    private final TokenService tokenService;

    @Bean
    @RequestScope
    public UserContext userContext(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        DecodedJWT decodedJWT = tokenService.verify(bearerToken);
        String login = ClaimsStrategy.LOGIN.getClaim(decodedJWT).toString();
        List<UserRole> role = (List<UserRole>) ClaimsStrategy.ROLE.getClaim(decodedJWT);
        Long id = (Long) ClaimsStrategy.ID.getClaim(decodedJWT);
        return UserContext.builder()
                .id(id)
                .userName(login)
                .role(role)
                .build();
    }
}
