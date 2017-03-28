package com.malei.bot.dao;

import java.io.Serializable;
import java.util.List;

public interface IUserDao<T,K extends Serializable> {
    public void create(T object);

    public void delete(T object);

    public void update(T object);

    public List<T> getAll();

    public T getById(K id);

    public T getByTelegramId(K id);

}
