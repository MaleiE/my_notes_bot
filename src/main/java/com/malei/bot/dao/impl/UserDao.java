package com.malei.bot.dao.impl;

import com.malei.bot.configuration.HibernateConfig;
import com.malei.bot.dao.IUserDao;
import com.malei.bot.entities.User;
import org.hibernate.*;
import org.hibernate.FetchMode;
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
        getSession().save(object);
    }

    @Override
    public void delete(User object) {
        getSession().delete(object);
    }

    @Override
    public void update(User object) {
        getSession().merge(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        Criteria criteria = getCriteria(getSession());
        criteria.setFetchMode("notes",FetchMode.JOIN);
        return (List<User>) criteria.list();
    }

    @Override
    public User getById(Integer id) {
        User user = (User)getCriteria(getSession()).add(Restrictions.eq("id",id)).uniqueResult();
        Hibernate.initialize(user.getNotes());
        return user;

    }

    @Override
    public User getByTelegramId(Integer id) {
        Criteria criteria =getCriteria(getSession());
        criteria.setFetchMode("notes",FetchMode.JOIN);
        return (User) criteria.add(Restrictions.eq("userIdTelegram",id)).uniqueResult();
    }

    @Override
    public User getUserByTelegramIdWithoutNotes(Integer id) {
        return (User)getCriteria(getSession()).add(Restrictions.eq("userIdTelegram",id)).uniqueResult();
    }

    @Override
    public Transaction getTransaction() {
        return getSession().getTransaction();
    }


}
