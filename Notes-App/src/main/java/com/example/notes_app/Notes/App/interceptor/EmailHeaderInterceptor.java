package com.example.notes_app.Notes.App.interceptor;

import com.example.notes_app.Notes.App.context.UserContext;
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
            filterChain.doFilter(request, response);
        }finally {
            UserContext.clear();
        }

    }
}
