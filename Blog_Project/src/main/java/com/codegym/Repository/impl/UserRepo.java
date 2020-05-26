package com.codegym.Repository.impl;

import com.codegym.Model.PostEntity;
import com.codegym.Model.UserEntity;
import com.codegym.Repository.IUserRepoHQL;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserRepo implements IUserRepoHQL {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public UserEntity findByUserName(String userName) {
        TypedQuery<UserEntity> query = em.createQuery("select u from UserEntity u where u.userName =: userName", UserEntity.class);
        query.setParameter("userName", userName);
        try {
            return query.getSingleResult();
        }catch(NoResultException n) {
            return null;}
    }


    public UserEntity findByEmail(String email) {
        TypedQuery<UserEntity> query = em.createQuery("select u from UserEntity u where u.email =: email", UserEntity.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        }catch(NoResultException n) {
            return null;}
    }

    public List<PostEntity> findPostByUser(Long userId) {
        TypedQuery<PostEntity> query = em.createQuery("select p from PostEntity p where p.userByUserId.id =: id",PostEntity.class);
        query.setParameter("id", userId);
        try {
            return query.getResultList();
        }catch(NoResultException n) {
            return null;}
    }

}
