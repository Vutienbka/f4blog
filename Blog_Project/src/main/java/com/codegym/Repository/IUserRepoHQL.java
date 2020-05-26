package com.codegym.Repository;

import com.codegym.Model.PostEntity;
import com.codegym.Model.UserEntity;

import java.util.List;

public interface IUserRepoHQL {
    UserEntity findByUserName(String userName);
    UserEntity findByEmail(String email);
    List<PostEntity> findPostByUser(Long id);
}
