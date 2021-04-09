package com.example.passwordlocker.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;

    private String name;

    @ManyToMany(targetEntity = User.class, mappedBy = "roles", cascade = CascadeType.ALL)
    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
