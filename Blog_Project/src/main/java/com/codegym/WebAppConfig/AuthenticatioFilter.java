//package com.codegym.WebAppConfig;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedReader;
//
//public class AuthenticatioFilter extends UsernamePasswordAuthenticationFilter {
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            BufferedReader reader = request.getReader();
//            StringBuffer sb = new StringBuffer();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            String parsedReq = sb.toString();
//            if (parsedReq != null) {
//                ObjectMapper mapper = new ObjectMapper();
//                AuthReq authReq = mapper.readValue(parsedReq, AuthReq.class);
//                return new UsernamePasswordAuthenticationToken(authReq.getUserName(), authReq.getPassword());
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new InternalAuthenticationServiceException("Failed to parse authentication request body");
//        }
//        return null;
//    }
//
//
//    public static class AuthReq {
//        String userName;
//        String password;
//
//        public String getUserName() {
//            return userName;
//        }
//
//        public void setUserName(String userName) {
//            this.userName = userName;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//    }
//}
