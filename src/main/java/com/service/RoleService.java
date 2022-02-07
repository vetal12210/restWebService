package com.service;

import com.dao.RoleDao;
import com.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@EnableTransactionManagement
@Transactional
public class RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    public void create(@Valid Role role) {
        roleDao.create(role);
    }

    @Transactional
    public void update(@Valid Role role) {
        roleDao.update(role);
    }

    @Transactional
    public void remove(@Valid Role role) {
        roleDao.delete(role);
    }

    @Transactional
    public Role findByName(@Valid String name) {
        return roleDao.findByName(name);
    }

}
