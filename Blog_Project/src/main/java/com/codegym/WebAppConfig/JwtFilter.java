package com.codegym.WebAppConfig;

import com.codegym.Service.IUserService;
import com.codegym.Service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    IUserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try { String bearerToken = request.getHeader("authorization");
            System.out.println(bearerToken);
        if (bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            System.out.println(token);
            String userName = this.jwtService.getUserName(token);
            System.out.println("thanh cong");
            UserDetails userDetails = userService.loadUserByUsername(userName);
            System.out.println(userName);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        } catch (Exception e) {
            System.out.println("test filter");
            logger.error("Can not set User authentication -> Message{}",e);
        }
        filterChain.doFilter(request,response);
    }

//    private String getJwtFromRequest(HttpServletRequest request) {
//        String jwt = request.getHeader("Authorization");
//        if (jwt != null && jwt.startsWith("Bearer ")) {
//            return jwt.replace("Bearer ", "");
//        } else {
//            return null;
//        }
//    }
}
