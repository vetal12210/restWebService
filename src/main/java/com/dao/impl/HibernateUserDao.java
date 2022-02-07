package com.dao.impl;

import com.dao.UserDao;
import com.entity.User;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
@Log4j2
public class HibernateUserDao implements UserDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateUserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findByLogin(String login) {
        log.info("Find user by login {}", login);
        User user = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<User> query = session.createQuery("from users where login=:login", User.class);
            query.setParameter("login", login);
            user = query.getSingleResult();
            log.info("Find user by login successfully");
        } catch (NoResultException e) {
            log.error("Can't find user by login {}", login, e.getMessage(), e);
        } catch (Exception e) {
            log.error("findByLogin method error ", e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        log.info("Find user by email {}", email);
        User user = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<User> query = session.createQuery("from users where email=:email", User.class);
            query.setParameter("email", email);
            user = query.getSingleResult();
            log.info("Find user by email successfully");
        } catch (NoResultException e) {
            log.error("Can't find user by email {}", email, e.getMessage(), e);
        } catch (Exception e) {
            log.error("findByEmail method error ", e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        log.info("Find user by id");
        User user = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("from users where id=:id", User.class);
            query.setParameter("id", id);
            user = (User) query.getSingleResult();
            log.info("Find user by id successfully");
        } catch (NoResultException e) {
            log.error("Can't find user by id {}", id, e.getMessage(), e);
        } catch (Exception e) {
            log.error("findById method error ", e.getMessage(), e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from users", User.class).getResultList();
    }

    @Override
    public void create(User user) {
        log.info("Create user {}", user);
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(user);
            log.info("{} was successfully created", user);
        } catch (Exception e) {
            if (session != null) {
                session.clear();
            }
            log.error("Can't create user {}", user);
        }
    }

    @Override
    public void update(User user) {
        log.info("Update user {}", user);
        try {
            Session session = sessionFactory.getCurrentSession();
            if (user != null) {
                session.update(user);
                log.warn("{} was successfully updating", user.getLogin());
            }
        } catch (Exception e) {
            log.error("Can't update user {}", user, e.getMessage(), e);
        }
    }


    @Override
    public void delete(User user) {
        log.info("Delete user {}", user);
        try {
            Session session = sessionFactory.getCurrentSession();
            if (user != null) {
                session.delete(user);
                log.warn("{} was successfully removed", user.getLogin());
            }
        } catch (Exception e) {
            log.error("Can't delete user {}", user, e.getMessage(), e);
        }
    }
}
