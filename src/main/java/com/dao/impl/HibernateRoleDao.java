package com.dao.impl;

import com.entity.Role;
import com.dao.RoleDao;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@Log4j2
public class HibernateRoleDao implements RoleDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateRoleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role findByName(String name) {
        log.info("Find role by name");
        Role role = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("from roles where name=:name", Role.class);
            query.setParameter("name", name);
            role = (Role) query.getSingleResult();
            log.info("Find role {} by name successfully", role.getName());
        } catch (NoResultException e) {
            log.error("Can't find role by name ", e.getMessage(), e);
        } catch (Exception e) {
            log.error("findByName method error ", e.getMessage(), e);
        }
        return role;
    }

    @Override
    public Role findById(Long id) {
        log.info("Find role by id");
        Role role = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("from roles where id=:id", Role.class);
            query.setParameter("id", id);
            role = (Role) query.getSingleResult();
            log.info("Find role {} by id successfully", role.getName());
        } catch (NoResultException e) {
            log.error("Can't find all roles ", e.getMessage(), e);
        } catch (Exception e) {
            log.error("findById method error ", e.getMessage(), e);
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from roles", Role.class).getResultList();
    }

    @Override
    public void create(Role role) {
        log.info("Create role {}", role);
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(role);
            log.info("{} was successfully created", role.getName());
        } catch (Exception e) {
            if (session != null) {
                session.clear();
            }
            log.error("Can't create role {}", role, e.getMessage(), e);
        }

    }

    @Override
    public void update(Role role) {
        log.info("Update role {}", role);
        try {
            Session session = sessionFactory.getCurrentSession();
            if (role != null) {
                session.update(role);
                log.warn("{} was successfully updated", role.getName());
            }
        } catch (Exception e) {
            log.error("Can't update role {}", role, e.getMessage(), e);
        }
    }

    @Override
    public void delete(Role role) {
        log.info("Delete role {}", role);
        try {
            Session session = sessionFactory.getCurrentSession();
            if (role != null) {
                session.delete(role);
                log.warn("{} was successfully deleted", role.getName());
            }
        } catch (Exception e) {
            log.error("Can't delete role {}", role, e.getMessage(), e);
        }
    }
}
