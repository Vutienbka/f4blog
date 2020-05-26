package com.codegym.Service;

import com.codegym.Model.MediaEntity;
import com.codegym.Model.PostEntity;
import com.codegym.Model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    List<UserEntity> findAll();

    void delete(Long id);

    void save(UserEntity userEntity);

    UserEntity findByUserName(String userName);

    UserEntity findByEmail(String email);

    UserEntity findById(Long id);

    List<PostEntity> findPostByUser(Long id);

}
