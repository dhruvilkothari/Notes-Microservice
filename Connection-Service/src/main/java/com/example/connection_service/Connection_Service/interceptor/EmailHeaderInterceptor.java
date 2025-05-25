package com.example.connection_service.Connection_Service.interceptor;

import com.example.connection_service.Connection_Service.context.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class EmailHeaderInterceptor extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String email = request.getHeader("X-User-Email");
        if (email != null) {
            UserContext.setEmail(email);

            System.out.println("Email from Gateway: " + email);
            // You can also store it in a ThreadLocal or SecurityContext if needed
        }
        try {
            System.out.println("Before filterChain");
            filterChain.doFilter(request, response);
            System.out.println("After filterChain");

        }finally {
            UserContext.clear();
        }

    }
}
