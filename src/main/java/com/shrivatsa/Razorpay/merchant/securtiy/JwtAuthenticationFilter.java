package com.shrivatsa.Razorpay.merchant.securtiy;

import com.shrivatsa.Razorpay.merchant.service.ApiKeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.webresources.JarWarResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil  jwtUtil;
    private final HandlerExceptionResolver  handlerExceptionResolver;
    private final MerchantContext  merchantContext;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Incoming request"+request.getRequestURI());

        try {
            final String authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request,response);
                return;
            }

            String token = authorizationHeader.substring("Bearer ".length());
            Claims claims = jwtUtil.verifyAccessToken(token);
            if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + jwtUtil.extractRole(claims)))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

                merchantContext.setMerchantId(UUID.fromString(jwtUtil.extractMerchantId(claims)));
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }
    }
}
