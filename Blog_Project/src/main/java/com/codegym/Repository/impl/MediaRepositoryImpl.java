package com.codegym.Repository.impl;

import com.codegym.Model.MediaEntity;
import com.codegym.Model.PostEntity;
import com.codegym.Repository.IMediaRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class MediaRepositoryImpl implements IMediaRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<MediaEntity> findAll() {
        TypedQuery<MediaEntity> query = em.createQuery("select c from MediaEntity c", MediaEntity.class);
        return query.getResultList();
    }

    @Override
    public MediaEntity findById(Long id) {
        TypedQuery<MediaEntity> query = em.createQuery("select c from MediaEntity c where  c.id=:id", MediaEntity.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(MediaEntity model) {
        if (model.getId() != null) {
            em.merge(model);
        }else {
            em.persist(model);
        }
    }

    @Override
    public void remove(Long id) {
        MediaEntity mediaEntity = findById(id);
        if (mediaEntity != null) {
            em.remove(mediaEntity);
        }
    }
}
