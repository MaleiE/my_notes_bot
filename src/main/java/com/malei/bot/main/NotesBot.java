package com.malei.bot.main;


import com.malei.bot.entities.Notes;
import com.malei.bot.key.KeyboardAdd;
import com.malei.bot.service.impl.ImplNotesService;
import com.malei.bot.service.impl.ImplUserService;
import org.joda.time.DateTime;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static com.malei.bot.key.KeyboardAdd.*;

public class NotesBot extends TelegramLongPollingBot{

    private static final int START = 0;
    private static final int MAIN_MENU = 1;
    private static final int NOTES = 2;
    private static final int NOTES_DATE = 3;


    private ImplUserService userService;
    private ImplNotesService notesService;

    private ImplNotesService getNotesService(){
        return notesService = new ImplNotesService();
    }

    private ImplUserService getUserService(){
        return userService = new ImplUserService();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            try {
                startBot(update.getMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }else {
            System.err.println("no mess.");
        }
    }

    private void startBot(Message message) throws TelegramApiException{
      if(message.hasText()||message.hasLocation()){
          final int step = getUserService().getStepUser(message.getFrom().getId(),message);
          System.out.println(message.getText()+" text");
          System.out.println(step+" step");
          SendMessage sendMessageReq=null;
          switch (step){
              case MAIN_MENU:
                  sendMessageReq=sendMainMenuMsg(message);
                  break;
              case NOTES:
                  sendMessageReq=createNotes(message);
                  break;
              case NOTES_DATE:
                    notesDate(message);
                    break;
              default:
                  System.out.println("deff");
                  sendMessageReq=sendStartMsg(message,getMainMenuKeyboard());
                  break;
          }

          sendMessage(sendMessageReq);
      }
    }

    private SendMessage notesDate(Message message){
        SendMessage sendMessage = new SendMessage();
       // if(message.getText().equals("Сегодня")){
            List<Notes> notes =getUserService().getUserNotesByTelegramId(message.getFrom().getId()).getNotes();
            Notes notes1 = notes.get(notes.size()-1);
            String text = message.getText();
            String[] date = text.split(" ");
            int i = Integer.parseInt(date[0]);
            int i1 = Integer.parseInt(date[1]);
            int i2 = Integer.parseInt(date[2]);
            int i3 = Integer.parseInt(date[3]);
            int i4 = Integer.parseInt(date[4]);

            notes1.setCreateDate(new DateTime());
            DateTime dateTime = new DateTime(i,i1,i2,i3,i4);
            notes1.setAlertDate(dateTime);

            getNotesService().updateNotes(notes1);
        System.out.println(text);
       // }
        return null;
    }

    private SendMessage createNotes(Message message){
        SendMessage sendMessage = new SendMessage();
        if(message.hasText()){
            Notes notes = new Notes();
            notes.setUser(getUserService().getUserNotesByTelegramId(message.getFrom().getId()));
            notes.setTextNotes(message.getText());
            getNotesService().createNotes(notes);
            sendMessage.setText("Задайте дату");
            sendMessage.disableNotification();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.enableMarkdown(true);
          //  sendMessage.setReplyMarkup(getDateKeyboard());
            System.out.println("Датау задай");
            getUserService().setStepUser(message.getFrom().getId(),NOTES_DATE);

        }else {
            sendMessage=sendStartMsg(message,getMainMenuKeyboard());
        }
        return sendMessage;
    }

    private SendMessage sendStartMsg(Message message,ReplyKeyboardMarkup keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(keyboard);
        sendMessage.disableNotification();
        sendMessage.setText("r");
        System.out.println("sendStartMsg  deff");
        getUserService().setStepUser(message.getFrom().getId(),MAIN_MENU);
        return sendMessage;
    }

    private SendMessage sendMainMenuMsg(Message message){
        SendMessage sendMessage=null;
        System.out.println(message.getText() + "sendMainMenuMsg");
        if(message.getText().equals("Создать уведомление")){
            sendMessage=sendNotesMsg(message);
        }else if(message.getText().equals("Настройки")){
         //   sendMessage=
        }else if (sendMessage.getText().equals("Все уведомления")) {
            //sendMessage =
        }else {
            sendMessage=sendStartMsg(message,getMainMenuKeyboard());
        }
        return sendMessage;

    }

    private SendMessage sendNotesMsg(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Введите уведомление");
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.disableNotification();
        getUserService().setStepUser(message.getFrom().getId(),NOTES);
        return sendMessage;
    }







    @Override
    public String getBotUsername() {
        return getProperties("telegram.bot.name");
    }

    @Override
    public String getBotToken() {
        return getProperties("telegram.bot.token");
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
