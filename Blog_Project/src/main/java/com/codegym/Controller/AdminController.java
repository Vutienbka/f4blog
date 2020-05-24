package com.codegym.Controller;

import com.codegym.Model.UserEntity;
import com.codegym.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(value = {"/api"})
public class AdminController {
    @Autowired
    IUserService userService;
    //--------------------------TOAN----------------------

    //--------------------------TIEN----------------------

    //--------------------------TU----------------------
    @GetMapping(value = "/getalluser", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserEntity> getAllUser() {
        List<UserEntity> list = this.userService.findAll();
        System.out.println(list.size());
        return list;
    }

    @GetMapping(value = "/getprofile/{userName}")
    public ResponseEntity<?> getUserByName(@PathVariable String userName) {
       return ResponseEntity.ok(this.userService.findByUserName(userName));
    }

    @DeleteMapping(value = "/deleteuser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUserById(@PathVariable String id) {
        try {
            this.userService.delete(Long.valueOf(java.lang.String.valueOf(id)));
        } catch (EmptyResultDataAccessException e) {
            return
                    new java.lang.String("Khong tim thay user");
        }

        return new String("xoa thanh cong");
    }

    @PostMapping(value = "/adduser")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity) {
            System.out.println(userEntity.getUserName() + " " + userEntity.getEmail());
            userEntity.setRegisteredAt(new Timestamp(new Date().getTime()));
            this.userService.save(userEntity);
        System.out.println("thanh cong");
            return ResponseEntity.ok(userEntity);
    }

    @PutMapping(value = "/edituser", produces = MediaType.APPLICATION_JSON_VALUE)
    public String editUser(@RequestBody UserEntity userEntity) {
        this.userService.save(userEntity);
        return new String("Sua thanh cong");
    }
    //--------------------------DUNG----------------------

}
