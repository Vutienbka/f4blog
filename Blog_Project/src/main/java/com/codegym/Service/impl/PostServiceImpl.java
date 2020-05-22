package com.codegym.Service.impl;

import com.codegym.Model.PostEntity;
import com.codegym.Repository.PostRepository;
import com.codegym.Service.IUserService;
import com.codegym.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostServiceImpl implements PostService {

    @Autowired
   PostRepository postRepository;

    @Override
    public List<PostEntity> findAll() {
        return (List<PostEntity>)postRepository.findAll();
    }

    @Override
    public PostEntity findById(Long id) {
        return postRepository.findOne(id);
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Override
    public void remove(Long id) {
        postRepository.delete(id);
    }

}
