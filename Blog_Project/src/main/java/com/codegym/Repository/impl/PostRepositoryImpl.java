package com.codegym.Repository.impl;

import com.codegym.Model.PostEntity;
import com.codegym.Repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class PostRepositoryImpl /*implements PostRepository*/ {

//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public List<PostEntity> findAll() {
//        TypedQuery<PostEntity> query = em.createQuery("select c from PostEntity c", PostEntity.class);
//        return query.getResultList();
//    }
//
//    @Override
//    public PostEntity findById(Long id) {
//        TypedQuery<PostEntity> query = em.createQuery("select c from PostEntity c where  c.id=:id", PostEntity.class);
//        query.setParameter("id", id);
//        try {
//            return query.getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }
//
//    @Override
//    public void save(PostEntity model) {
//        if (model!= null) {
//            em.merge(model);
//        } else {
//            em.persist(model);
//        }
//    }
//
//    @Override
//    public void remove(Long id) {
//        PostEntity postEntity = findById(id);
//        if (postEntity != null) {
//            em.remove(postEntity);
//        }
//    }

}
