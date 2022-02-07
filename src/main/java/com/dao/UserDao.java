package com.dao;

import com.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    void create(User user);

    void update(User user);

    void delete(User user);

    List<User> findAll();

    User findByLogin(String login);

    User findByEmail(String email);
}
