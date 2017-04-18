package com.malei.bot.dao.impl;

import com.malei.bot.configuration.HibernateConfig;
import com.malei.bot.dao.INotesDao;
import com.malei.bot.entities.Notes;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class NotesDao implements INotesDao<Notes,Long>{
    private SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    private Criteria getCriteria(Session session){
        return getSession().createCriteria(Notes.class);
    }


    @Override
    public void create(Notes object) {
        getSession().getTransaction().begin();
        getSession().save(object);
        getSession().getTransaction().commit();
    }

    @Override
    public void delete(Notes object) {
        getSession().getTransaction().begin();
        getSession().delete(object);
        getSession().getTransaction().commit();
    }

    @Override
    public void update(Notes object) {
        getSession().getTransaction().begin();
        getSession().merge(object);
        getSession().getTransaction().commit();
    }

    @Override
    public void deleteAll() {
        String hql = String.format("delete from %s","Notes");
        Query q = getSession().createQuery(hql);
        q.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Notes> getAll() {
        getSession().getTransaction().begin();
        List<Notes> notes = getCriteria(getSession()).list();
        getSession().getTransaction().commit();
        return notes;
    }

    @Override
    public Notes getById(Long id) {
        getSession().getTransaction().begin();
        Notes notes = (Notes)getCriteria(getSession()).add(Restrictions.eq("id",id)).uniqueResult();
        getSession().getTransaction().commit();
        return notes;
    }
}
