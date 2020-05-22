package com.codegym.Service;

import com.codegym.Model.MediaEntity;
import com.codegym.Model.PostEntity;
import com.codegym.Repository.Repository;

import java.util.List;

public interface IMediaService {
    List<MediaEntity> findAll();

    MediaEntity findById(Long id);

    void save(MediaEntity mediaEntity);

    void remove(Long id);
}
