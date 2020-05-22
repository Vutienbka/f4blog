package com.codegym.Repository;

import com.codegym.Model.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IUserRepo extends PagingAndSortingRepository<UserEntity,Long> {
}
