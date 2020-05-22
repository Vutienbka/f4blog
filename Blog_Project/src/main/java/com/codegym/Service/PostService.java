package com.codegym.Service;

import com.codegym.Model.PostEntity;
import com.codegym.Repository.PostRepository;

import java.util.List;

public interface PostService  {
    List<PostEntity> findAll();

    PostEntity findById(Long id);

    PostEntity save(PostEntity postEntity);

    void remove(Long id);

}
