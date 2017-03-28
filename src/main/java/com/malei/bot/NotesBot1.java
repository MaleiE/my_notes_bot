package com.malei.bot;

import com.malei.bot.entities.Notes;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
/*
import static com.malei.bot.key.KeyboardAdd.getMainMenuKeyboard;

import org.joda.time.LocalDate;

public class NotesBot extends TelegramLongPollingBot {
    private int i =0;
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(i==1){
            createNotes(message);
            System.out.println(message.getText());
            i--;
        }
            if (message.hasText()){
                String textMessage = message.getText();
                    switch (textMessage){
                        case "/start":
                            startMsg(message);
                            break;
                        case "Создать уведомление":
                            createMsg(message);
                            i++;
                            break;
                        case "test":
                            createTest(message);
                            break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return getProperties("telegram.bot.name");
    }

    @Override
    public String getBotToken() {
        return getProperties("telegram.bot.token");
    }

    private void createTest(Message message){
        //System.out.println("URRAAA");
    }

    private void startMsg(Message message){

        sendMsg(message,getMainMenuKeyboard());

    }

    private void createMsg(Message message){
        sendMsg(message,"О чем напомнить?");
        //System.out.println(message.getText());
        Notes notes = new Notes();
    }

    private void createNotes(Message message){
        Notes notes = new Notes();
        notes.setAlertDate(new LocalDate(8858L));
        notes.setCreateDate(new LocalDate(312312));
        List<Notes> notes1 = new ArrayList<>();
        notes.setTextNotes(message.getText());
        UserDao userDao = new UserDao();
        if (userDao.getByTelegramId(message.getFrom().getId())!=null){
            com.malei.bot.entities.User user = userDao.getByTelegramId(message.getFrom().getId());
            notes1.add(notes);
            user.setNotes(notes1);
            userDao.update(user);
        }
    }


    private KeyboardButton keyboardButton(String text,Boolean loc){
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestLocation(loc);
        keyboardButton.setText(text);
        return keyboardButton;
    }

    private KeyboardRow keyboardRow(List<KeyboardButton> keyboardButtons){
        KeyboardRow keyboardRow = new KeyboardRow();
        for (KeyboardButton keyboardButton : keyboardButtons) {
            keyboardRow.add(keyboardButton);
        }
        return keyboardRow;
    }
    private ReplyKeyboardMarkup keyboard(List<KeyboardRow> keyboardRows){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        return replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    private void sendMsg(Message message,ReplyKeyboardMarkup keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(keyboard);
        sendMessage.disableNotification();
        sendMessage.setText("r");
        try {
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendMsg(Message message,InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.disableNotification();
        sendMessage.setText("d");
        try {
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendMsg(Message message,String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        //sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.disableNotification();
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getProperties(String nameProp){
        Properties property = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/appConfig.properties");
            property.load(fis);
            return property.getProperty(nameProp);
        } catch (IOException e) {
            System.err.println("Ошибочка"+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
*/