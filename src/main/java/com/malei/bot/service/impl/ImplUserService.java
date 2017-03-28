package com.malei.bot.service.impl;

import com.malei.bot.service.IUserService;
import com.malei.bot.dao.impl.UserDao;
import com.malei.bot.entities.User;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

public class ImplUserService implements IUserService{

    UserDao userDao = new UserDao();

    @Override
    public void createUser(User ob) {
        userDao.create(ob);
    }

    @Override
    public void updateUser(User ob) {
        userDao.update(ob);
    }

    @Override
    public void deleteUser(User ob) {
        userDao.delete(ob);
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public void setStepUser(Integer telegramUserId,int step) {
        User user =userDao.getByTelegramId(telegramUserId);
        user.setStep(step);
        userDao.update(user);
    }

    @Override
    public User getUserByID(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public Integer getStepUser(Integer telegramUserId, Message message) {
        int i = 0;
        if(userDao.getByTelegramId(telegramUserId)!=null){
            i=userDao.getByTelegramId(telegramUserId).getStep();
        }else {
            User user = new User();
            user.setUserIdTelegram(message.getFrom().getId());
            user.setName(message.getFrom().getFirstName());
            user.setStep(0);
            userDao.create(user);
        }

        return i;
    }

    @Override
    public User getUserNotesByTelegramId(Integer telegramId) {
        return userDao.getByTelegramId(telegramId);
    }
}
