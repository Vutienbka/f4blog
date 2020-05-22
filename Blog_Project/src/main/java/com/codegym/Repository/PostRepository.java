package com.codegym.Repository;


import com.codegym.Model.PostEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends CrudRepository<PostEntity,Long> {
}
