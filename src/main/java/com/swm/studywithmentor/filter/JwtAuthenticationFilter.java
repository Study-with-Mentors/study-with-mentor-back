package com.swm.studywithmentor.filter;

import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final HandlerExceptionResolver resolver;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserService userService,
        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            // Will throw exception if jwt is invalid
            tokenProvider.validateToken(jwt);

            String usersEmail = tokenProvider.getEmailFromJwt(jwt);
            UserDetails userDetails = userService.loadUserByUsername(usersEmail);

            // If email is valid, set auth for security context
            UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Pass user to request scope
            // so that controller can retrieve it later, if needed
            request.setAttribute("user", userDetails);
            filterChain.doFilter(request, response);
        } catch (ApplicationException ex) {
            // Throw exception to ApplicationExceptionHandler
            resolver.resolveException(request, response, null, ex);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
