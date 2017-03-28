package com.malei.bot.dao.impl;

import com.malei.bot.configuration.HibernateConfig;
import com.malei.bot.dao.IUserDao;
import com.malei.bot.entities.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDao implements IUserDao<User,Integer> {
    private SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private Criteria getCriteria(Session session){
        return getSession().createCriteria(User.class);
    }


    @Override
    public void create(User object) {
        getSession().getTransaction().begin();
        getSession().save(object);
        getSession().getTransaction().commit();
    }

    @Override
    public void delete(User object) {
        getSession().getTransaction().begin();
        getSession().delete(object);
        getSession().getTransaction().commit();
    }

    @Override
    public void update(User object) {
        getSession().getTransaction().begin();
        getSession().merge(object);
        getSession().getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        getSession().getTransaction().begin();
        List<User> users = getCriteria(getSession()).list();
        getSession().getTransaction().commit();
        return users;
    }

    @Override
    public User getById(Integer id) {
        getSession().getTransaction().begin();
        User user = (User)getCriteria(getSession()).add(Restrictions.eq("id",id)).uniqueResult();
        getSession().getTransaction().commit();
        return user;
    }

    @Override
    public User getByTelegramId(Integer id) {
        getSession().getTransaction().begin();
        User user = (User)getCriteria(getSession()).add(Restrictions.eq("userIdTelegram",id)).uniqueResult();
        getSession().getTransaction().commit();
        return user;
    }



}
