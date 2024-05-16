package com.example.finance.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Component
@AllArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ApiEndpoints apiEndpoint = ApiEndpoints.from(request.getRequestURI());
        if (!apiEndpoint.isAuthorization()) {
            return true;
        }
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        DecodedJWT decodedJWT = tokenService.verify(bearerToken);
        List<UserRole> userRole = (List<UserRole>) ClaimsStrategy.ROLE.getClaim(decodedJWT);
        if (!userRole.contains(apiEndpoint.getRole())) {
            throw new JWTVerificationException("Unauthorized");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        ApiEndpoints apiEndpoint = ApiEndpoints.from(request.getRequestURI());
        if (!apiEndpoint.isAuthorization()) {
            return;
        }
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshedToken = tokenService.refreshBearerToken(bearerToken);
        response.addHeader(HttpHeaders.AUTHORIZATION, refreshedToken);
        response.addHeader("test", "test");
    }
}
