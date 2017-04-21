package com.malei.bot.service.impl;

import com.malei.bot.service.IUserService;
import com.malei.bot.dao.impl.UserDao;
import com.malei.bot.entities.User;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

import static com.malei.bot.utilities.Utilities.getTimeZoneLocation;

public class ImplUserService implements IUserService{

    UserDao userDao = new UserDao();

    @Override
    public void createUser(User ob) {
        try {
            userDao.getTransaction().begin();
            userDao.create(ob);
            userDao.getTransaction().commit();
        }catch (Exception e) {
            userDao.getTransaction().rollback();
        }
    }

    @Override
    public void updateUser(User ob) {
        try {
            userDao.getTransaction().begin();
            userDao.update(ob);
            userDao.getTransaction().commit();
        }catch (Exception e) {
            userDao.getTransaction().rollback();
        }
    }

    @Override
    public void deleteUser(User ob) {
        try {
            userDao.getTransaction().begin();
            userDao.delete(ob);
            userDao.getTransaction().commit();
        }catch (Exception e) {
            userDao.getTransaction().rollback();
        }
        userDao.delete(ob);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = null;
        try {
            userDao.getTransaction().begin();
            users= userDao.getAll();
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }
        return users;

    }

    @Override
    public void setStepUser(Integer telegramUserId,int step) {
        try {
            userDao.getTransaction().begin();
            User user =userDao.getByTelegramId(telegramUserId);
            user.setStep(step);
            userDao.update(user);
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }

    }

    @Override
    public User getUserByID(Integer id) {
        User user = null;
        try {
            userDao.getTransaction().begin();
            user = userDao.getById(id);
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }
        return user;
    }



    @Override
    public Integer getStepUser(Integer telegramUserId, Message message) {
        int step = 0;
        try {
            userDao.getTransaction().begin();
            if(userDao.getByTelegramId(telegramUserId)!=null){
                step=userDao.getByTelegramId(telegramUserId).getStep();
            }else {
                User user = new User();
                user.setUserIdTelegram(message.getFrom().getId());
                user.setName(message.getFrom().getFirstName());
                user.setStep(0);
                userDao.create(user);
            }
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }
        return step;
    }

    @Override
    public User getUserNotesByTelegramId(Integer telegramId) {
        User user = null;
        try {
            userDao.getTransaction().begin();
            user = userDao.getByTelegramId(telegramId);
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }

        return user;
    }

    @Override
    public void setUserTimeZone(Message message) {
        try {
            userDao.getTransaction().begin();
            String timeZone = getTimeZoneLocation(message);
            User user = userDao.getByTelegramId(message.getFrom().getId());
            user.setTimeZone(timeZone);
            userDao.update(user);
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }

    }

    @Override
    public String getUserLang(Integer telegramUserId) {
         String loc= null;
        try {
            userDao.getTransaction().begin();
            User user = userDao.getByTelegramId(telegramUserId);
            loc =user.getLocal();
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }

        return loc;
    }

    @Override
    public void setUserLang(String message,Integer telegramUserId) {
        try {
            userDao.getTransaction().begin();
            User user = userDao.getByTelegramId(telegramUserId);
            user.setLocal(message);
            userDao.update(user);
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }

    }

    @Override
    public String getUserTimeZone(Message message) {
        String timeZone = null;
        try {
            userDao.getTransaction().begin();
            timeZone = userDao.getByTelegramId(message.getFrom().getId()).getTimeZone();
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }
        return timeZone;
    }

    @Override
    public User getUserByTelegramIdWithoutNotes(Integer id) {
        User user = null;
        try {
            userDao.getTransaction().begin();
            user = userDao.getUserByTelegramIdWithoutNotes(id);
            userDao.getTransaction().commit();
        }catch (Exception e){
            userDao.getTransaction().rollback();
        }
        return user;
    }

    public String getUserTimeZone(Integer telegramId) {
        return userDao.getByTelegramId(telegramId).getTimeZone();
    }
}
