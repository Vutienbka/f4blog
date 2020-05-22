package com.codegym.Service.impl;

import com.codegym.Model.MediaEntity;
import com.codegym.Model.PostEntity;
import com.codegym.Repository.IMediaRepository;
import com.codegym.Service.IMediaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MediaService implements IMediaService {

    @Autowired
    IMediaRepository mediaRepository;

    @Override
    public List<MediaEntity> findAll() {
        return mediaRepository.findAll();
    }

    @Override
    public MediaEntity findById(Long id) {
        return mediaRepository.findById(id);
    }

    @Override
    public void save(MediaEntity media) {
        mediaRepository.save(media);
    }

    @Override
    public void remove(Long id) {
        mediaRepository.remove(id);
    }
}
