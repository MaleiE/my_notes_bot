package com.malei.bot.dao;

import java.io.Serializable;
import java.util.List;

public interface INotesDao<T,K extends Serializable> {

    public void create(T object);

    public void delete(T object);

    public void update(T object);

    public void  deleteAll();

    public List<T> getAll();

    public T getById(K id);

}
