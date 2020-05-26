package com.codegym.Controller;

import com.codegym.Model.UserEntity;
import com.codegym.Model.UserJwt;
import com.codegym.Service.IUserService;
import com.codegym.Service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@CrossOrigin("*")
@RestController
@RequestMapping(value = {"/"})
public class WebController {
    @Autowired
    IUserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity userEntity) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userEntity.getUserName(),userEntity.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = this.jwtService.generateTokenLogin(userEntity.getUserName());

//        System.out.print(userService.findPostByUser(1L).size());
        System.out.println(userEntity.getUserName()+".."+userEntity.getPassword());
        UserEntity userEntity1 = this.userService.findByUserName(userEntity.getUserName());

        Collection collec =  Collections.singleton(new SimpleGrantedAuthority(userEntity1.getRoleEntityList().iterator().next().getRoleName()));
        return ResponseEntity.ok(new UserJwt(userEntity1.getId(),token,userEntity1.getUserName(),collec,userEntity1.getSrcAvatar()));
    }

}
