package com.dao;

import com.entity.Role;

public interface RoleDao extends Dao<Role>{
    void create(Role role);

    void update(Role role);

    void delete(Role role);

    Role findByName(String name);
}
