package com.codegym.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role", schema = "project")
public class RoleEntity {
    private long id;
    private String roleName;
    private List<UserEntity> userEntityList;

    public RoleEntity() {
    }

    public RoleEntity(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "roleName",unique = true)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    @ManyToMany(mappedBy = "roleEntityList", fetch = FetchType.LAZY,cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }
}
