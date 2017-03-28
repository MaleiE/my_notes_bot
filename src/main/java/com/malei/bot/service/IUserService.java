package com.malei.bot.service;

import com.malei.bot.entities.User;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

public interface IUserService {

    public void createUser(User ob);

    public void updateUser(User ob);

    public void deleteUser (User ob);

    public List<User> getAllUser();

    public void setStepUser(Integer telegramUserId,int step);

    public User getUserByID(Integer id);

    public Integer getStepUser(Integer telegramUserId, Message message);

    public User getUserNotesByTelegramId(Integer telegramId);

}
