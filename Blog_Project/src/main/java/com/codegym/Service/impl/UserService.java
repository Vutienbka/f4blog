package com.codegym.Service.impl;

import com.codegym.Model.PostEntity;
import com.codegym.Model.RoleEntity;
import com.codegym.Model.UserEntity;
import com.codegym.Repository.IUserRepo;
import com.codegym.Repository.IUserRepoHQL;
import com.codegym.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    BCryptPasswordEncoder be;
    @Autowired
    IUserRepoHQL userRepoHQL;
    @Autowired
    IUserRepo userRepo;

    public List<UserEntity> findAll() {
        List<UserEntity> list = (List<UserEntity>) this.userRepo.findAll();
        System.out.println(list.get(0).getPassword());
        return list;
    }

    public void delete(Long id) {
        this.userRepo.delete(id);
    }

    public void save(UserEntity userEntity) {
        if (userEntity.getId() == 0L) {
            Set list = new LinkedHashSet<RoleEntity>();
            list.add(new RoleEntity(1L,"ROLE_USER"));
            userEntity.setRoleEntityList(list);
        }
//        userEntity.setPassword(MyUtil.passwordEncoder().encode(userEntity.getPassword()));
        userEntity.setPassword(this.be.encode(userEntity.getPassword()));
        this.userRepo.save(userEntity);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        UserEntity userEntity = this.userRepoHQL.findByUserName(userName);
        return userEntity;
    }

    @Override

    public UserEntity findByEmail(String email) {
        return this.userRepoHQL.findByEmail(email);}

    public UserEntity findById(Long id) {
        return userRepo.findOne(id);
    }

    @Override
    public List<PostEntity> findPostByUser(Long id) {
        return userRepoHQL.findPostByUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = this.findByUserName(userName);
        if (userEntity == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        Set<RoleEntity> listRole = userEntity.getRoleEntityList();
        for (RoleEntity userrole: listRole) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(userrole.getRoleName()));
        }
        User userDetail = new User(userEntity.getUserName(),userEntity.getPassword(),grantedAuthorityList);

        return userDetail;
    }
}
