package com.cricket.User.Application.filter;

import com.cricket.User.Application.constants.AppConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Since this filter is injected after the basic authentication filter,
        // i.e the authentication filter of my end user will be successful by the time this filter is invoked.
        // Here we are getting the current authenticated user details with the help of SecurityContextHolder.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null!= authentication){
            SecretKey secretKey = Keys.hmacShaKeyFor(AppConstants.SECURITY_JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            String jwtToken = Jwts.builder()
                    .setIssuer("User Application").setSubject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + AppConstants.SECURITY_JWT_EXPI))
                    .signWith(secretKey).compact();
            response.setHeader(AppConstants.SECURITY_JWT_HEADER_STRING,jwtToken);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/user/login");
    }

    // Reads all your authority and froms a string value and returns the set of authorities with delimiter ","
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authorities = new HashSet<>();
        for (GrantedAuthority grantedAuthority : collection) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return String.join(",", authorities);
    }
}
