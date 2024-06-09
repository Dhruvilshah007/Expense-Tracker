package com.ds.expensetracker.authentication.config;

import com.ds.expensetracker.authentication.service.BlacklistedTokenService;
import com.ds.expensetracker.authentication.util.JwtUtility;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

//    If the token is invalid, reject the request if the token is invalid or continues otherwise.
//    If the token is valid, extract the username, find the related user in the database, and set it in the authentication context so you can access it in any application layer.


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private BlacklistedTokenService blacklistedTokenService;

    private final JwtUtility jwtUtility;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtUtility jwtUtility,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtUtility = jwtUtility;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,@NotNull HttpServletResponse response,@NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        try{
            final String jwt=authHeader.substring(7);

            // Check if the token is blacklisted
            if (blacklistedTokenService.isTokenBlacklisted(jwt)) {
                throw new ApplicationException(
                        HttpStatusCode.valueOf(401 ),
                        "Unauthorized Access",
                        "Token is blacklisted"
                );
            }

            final String userEmail=jwtUtility.extractUsername(jwt);

            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if(jwtUtility.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


            filterChain.doFilter(request,response);

        }catch (Exception exception){
            handlerExceptionResolver.resolveException(request,response,null,exception);
        }
    }
}
