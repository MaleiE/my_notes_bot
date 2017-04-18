package com.malei.bot.service.impl;

import com.malei.bot.service.IUserService;
import com.malei.bot.dao.impl.UserDao;
import com.malei.bot.entities.User;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

import static com.malei.bot.utilities.Utilities.getTimeZoneLocation;

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
        return userDao.getAll();
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
        int step = 0;
        if(userDao.getByTelegramId(telegramUserId)!=null){
            step=userDao.getByTelegramId(telegramUserId).getStep();
        }else {
            User user = new User();
            user.setUserIdTelegram(message.getFrom().getId());
            user.setName(message.getFrom().getFirstName());
            user.setStep(0);
            userDao.create(user);
        }

        return step;
    }

    @Override
    public User getUserNotesByTelegramId(Integer telegramId) {
        return userDao.getByTelegramId(telegramId);
    }

    @Override
    public void setUserTimeZone(Message message) {
        String timeZone = getTimeZoneLocation(message);
        User user = userDao.getByTelegramId(message.getFrom().getId());
        user.setTimeZone(timeZone);
        userDao.update(user);
    }

    @Override
    public String getUserTimeZone(Message message) {
        return userDao.getByTelegramId(message.getFrom().getId()).getTimeZone();
    }
    public String getUserTimeZone(Integer telegramId) {
        return userDao.getByTelegramId(telegramId).getTimeZone();
    }
}
