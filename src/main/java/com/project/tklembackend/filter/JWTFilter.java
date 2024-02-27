package com.project.tklembackend.filter;

import com.project.tklembackend.model.Roles;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.repository.UserEntityRepository;
import com.project.tklembackend.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserEntityRepository userEntityRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getRequestURI().contains("/api/auth/") && !request.getRequestURI().contains("socket") ){
            String token = request.getHeader("AUTHORIZATION").replace("Bearer ","");
            String extractedToken = jwtService.extractJWT(token);

            UserEntity user = userEntityRepository.findByEmail(extractedToken).orElseThrow(
                    () -> new NoSuchElementException("User not found for email: " +extractedToken)
            );

            Roles userRole = user.getRole().getRoleName();

            //If the request are for the dashboard they will be blocked if the user is not from ADMIN role before it processed.
            if(request.getRequestURI().contains("admin") && !userRole.equals(Roles.ADMIN)){
                throw new AuthorizationServiceException("this user is not authorized to perform the request");
            }

            if(user.isEnabled()){
                Authentication authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                throw new DisabledException("Account is disabled");
            }
        }
        filterChain.doFilter(request,response);
    }
}
