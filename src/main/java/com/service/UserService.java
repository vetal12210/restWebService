package com.service;

import com.dao.UserDao;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@EnableTransactionManagement
@Transactional
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void create(@Valid User user) {
        userDao.create(user);
    }

    @Transactional
    public void update(@Valid User user) {
        userDao.update(user);
    }

    @Transactional
    public void remove(@Valid User user) {
        userDao.delete(user);
    }

    @Transactional
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    public User findByLogin(@Valid String login) {
        return userDao.findByLogin(login);
    }

    @Transactional
    public User findByEmail(@Valid String email) {
        return userDao.findByEmail(email);
    }

}