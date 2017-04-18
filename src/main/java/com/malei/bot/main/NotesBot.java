package com.malei.bot.main;


import com.malei.bot.entities.Notes;
import com.malei.bot.entities.User;
import com.malei.bot.service.impl.ImplNotesService;
import com.malei.bot.service.impl.ImplUserService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

import static com.malei.bot.key.KeyboardAdd.*;
import static com.malei.bot.utilities.Utilities.checkingUserTime;
import static com.malei.bot.utilities.Utilities.intToMonth;
import static com.malei.bot.utilities.Utilities.monthToInt;

public class NotesBot extends TelegramLongPollingBot{

    private static final int LOCATION_REQUEST = 0;
    private static final int LOCATION_PROCESSING = 10;
    private static final int MAIN_MENU = 1;
    private static final int CREATION_NOTE = 2;
    private static final int NOTE_CHOICE_DAY = 3;
    private static final int NOTES_STEP_HOUR = 4;
    private static final int NOTES_STEP_MINUTE = 5;
    private static final int NOES_FINAL = 6;
    private static final int GET_ALL_NOTES = 7;
    private static final int NOTES_MONTH = 8;
    private static final int NOTES_MONTH_DAY = 9;

    public NotesBot() {
        super();
        startNotes();

    }

    private ImplUserService userService;
    private ImplNotesService notesService;

    private ImplNotesService getNotesService(){
        return notesService = new ImplNotesService();
    }

    private ImplUserService getUserService(){
        return userService = new ImplUserService();
    }

    private void startNotes(){
        List<User> users=getUserService().getAllUser();
        for (User user:users) {
            List<Notes> notes = user.getNotes();
            for (Notes notes1:notes){
                Long time;
                DateTime now = new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(user.getUserIdTelegram())));
                DateTime alert = new DateTime(notes1.getAlertDate(),DateTimeZone.forID(getUserService().getUserTimeZone(user.getUserIdTelegram())));
                time = alert.getMillis()-now.getMillis();
                sendMessageFuture(notes1.getChatId(),time,notes1.getId(),user.getUserIdTelegram());
            }
        }
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
            if(update.hasCallbackQuery()){
                Long id = Long.parseLong(update.getCallbackQuery().getData());
                if(getNotesService().getNotesByID(id)!=null) {
                    getUserService().getUserNotesByTelegramId(update.getCallbackQuery().getFrom().getId()).getStringScheduledFuture().get(id).cancel(false);
                    getUserService().getUserNotesByTelegramId(update.getCallbackQuery().getFrom().getId()).getStringScheduledFuture().remove(id);
                    getNotesService().deleteNotes(getNotesService().getNotesByID(id));
                }
            }
        }
    }

    private void startBot(Message message) throws TelegramApiException{
        if(message.hasText()||message.hasLocation()){
            if(!message.hasLocation()&&message.getText().equals("EDIT")){
                System.out.println(message.getText());
            }

            final int step = getUserService().getStepUser(message.getFrom().getId(),message);
            SendMessage sendMessageReq;
                switch (step){
                    case LOCATION_REQUEST:
                        sendMessageReq = sendLocMess(message);
                        break;
                    case LOCATION_PROCESSING:
                        sendMessageReq = procLocMess(message);
                        break;
                    case MAIN_MENU:
                        sendMessageReq=sendMainMenuMsg(message);
                        break;
                    case CREATION_NOTE:
                        sendMessageReq=createNotes(message);
                        break;
                    case NOTE_CHOICE_DAY:
                    case NOES_FINAL:
                    case NOTES_STEP_HOUR:
                    case NOTES_STEP_MINUTE:
                    case NOTES_MONTH:
                    case NOTES_MONTH_DAY:
                        sendMessageReq=createNotesDate(message,step);
                        break;
                    case GET_ALL_NOTES:
                        sendMessageReq=allNotes(message);
                        break;
                    default:
                        sendMessageReq=sendStartMsg(message,getMainMenuKeyboard());
                        break;
                }

            sendMessage(sendMessageReq);
        }
    }


    private void sendMessageFuture(Long chatId, Long time,Long id,Integer userId){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
         ScheduledFuture<?> scheduledFuture =service.scheduleWithFixedDelay(() -> {
            SendMessage sendMessage = new SendMessage();
            Notes notes1 = getNotesService().getNotesByID(id);
            sendMessage.setText("\n"+"То о чем вы хотели не забыть:"+"\n"+"\n"+notes1.getTextNotes());
            sendMessage.disableNotification();
            sendMessage.setChatId(chatId);
            sendMessage.enableMarkdown(true);
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Отключить");
            button.setCallbackData(id.toString());
            row.add(button);
            keyboard.add(row);
            markup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(markup);
            try {
                sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }, time, 30000L, TimeUnit.MILLISECONDS);

         getUserService().getUserNotesByTelegramId(userId).setStringScheduledFuture(id,scheduledFuture);
    }
/**
 * Начало работы отправка местоположения
 */
    private SendMessage sendLocMess(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.disableNotification();
        sendMessage.setText("Нажми на кнопку");
        sendMessage.setReplyMarkup(getLocKeyboard());
        getUserService().setStepUser(message.getFrom().getId(),LOCATION_PROCESSING);
        return sendMessage;
    }
/**
 *  обработка местоположения, отправка главного меню
 */
    private SendMessage procLocMess(Message message){
        SendMessage sendMessage;
        if (message.hasLocation()) {
            sendMessage = sendStartMsg(message, getMainMenuKeyboard());
            getUserService().setUserTimeZone(message);
            getUserService().setStepUser(message.getFrom().getId(), MAIN_MENU);
        }
        else {
            sendMessage = sendLocMess(message);
        }
        return sendMessage;
    }
/**
 *  Создание уведомления по шагам
 */
    private SendMessage  createNotesDate(Message message, int step){
        SendMessage sendMessage;
        switch (step){
            case NOTE_CHOICE_DAY:
                sendMessage = notesDate(message);
                break;
            case NOTES_STEP_HOUR:
                sendMessage = hourNoteMes(message);
                break;
            case NOES_FINAL:
                sendMessage = notesFinalDate(message);
                break;
            case NOTES_MONTH:
                sendMessage = notesMonth(message);
                break;
            case NOTES_MONTH_DAY:
                sendMessage = calendarStepDayNotesMes(message);
                break;
            default:
                sendMessage=createNotes(message);
                break;
        }
        return sendMessage;
    }
/**
 *  В случае ошибки ввода числа в месяце
 */
    private  SendMessage notesErrMonth(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.disableNotification();
        sendMessage.setText("Выберите число");
        List<Notes> notes =getUserService().getUserNotesByTelegramId(message.getFrom().getId()).getNotes();
        Notes notes1 = notes.get(notes.size()-1);
        DateTime dateTime = new DateTime(notes1.getAlertDate(),DateTimeZone.forID(getUserService().getUserTimeZone(message)));
        Integer todayMonth = 1;
        String month = intToMonth(dateTime.getMonthOfYear());
        if(new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message))).getMonthOfYear()==monthToInt(month)){
            todayMonth=new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message))).getDayOfMonth();
        }
        switch (month){
            case("Январь"):
            case("Март"):
            case("Май"):
            case("Июль"):
            case("Август"):
            case("Октябрь"):
            case("Декабрь"):
                sendMessage.setReplyMarkup(getDayMonthKeyboard(todayMonth,31));
                break;
            case("Апрель"):
            case("Июнь"):
            case("Ноябрь"):
            case("Сентябрь"):
                sendMessage.setReplyMarkup(getDayMonthKeyboard(todayMonth,30));
                break;
            case("Февраль"):
                sendMessage.setReplyMarkup(getDayMonthKeyboard(todayMonth,28));
                break;
            default:
                System.err.println("default");
                sendMessage=calendarMesNotes(message);
                return sendMessage;
        }
        return sendMessage;
    }

/**
 *  Ввод числа в месяце
 */
    private SendMessage notesMonth(Message message){
        String month = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.disableNotification();
        sendMessage.setText("Выберите число");
        Integer todayMonth = 1;
        if(new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message))).getMonthOfYear()==monthToInt(month)){
            todayMonth=new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message))).getDayOfMonth();
        }
        switch (month){
            case("Январь"):
            case("Март"):
            case("Май"):
            case("Июль"):
            case("Август"):
            case("Октябрь"):
            case("Декабрь"):
                sendMessage.setReplyMarkup(getDayMonthKeyboard(todayMonth,31));
                break;
            case("Апрель"):
            case("Июнь"):
            case("Ноябрь"):
            case("Сентябрь"):
                sendMessage.setReplyMarkup(getDayMonthKeyboard(todayMonth,30));
                break;
            case("Февраль"):
                sendMessage.setReplyMarkup(getDayMonthKeyboard(todayMonth,28));
                break;
            default:
                System.err.println("default");
                sendMessage=calendarMesNotes(message);
                return sendMessage;
        }

        setMonth(message,month);

        return sendMessage;

    }
/**
 *  Ввод месяца
 */
    private void setMonth(Message message,String month){
        List<Notes> notes =getUserService().getUserNotesByTelegramId(message.getFrom().getId()).getNotes();
        Notes notes1 = notes.get(notes.size()-1);
        DateTime currentTime = new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message)));
        DateTime time;
        if(currentTime.getMonthOfYear()>monthToInt(month)){
            time = new DateTime(currentTime).monthOfYear().setCopy(monthToInt(month)).plusYears(1);
        }else {
            time = new DateTime(currentTime).monthOfYear().setCopy(monthToInt(month));
        }
        Timestamp alertDateDB = new Timestamp(time.getMillis());
        notes1.setAlertDate(alertDateDB);
        getNotesService().updateNotes(notes1);
        getUserService().setStepUser(message.getFrom().getId(),NOTES_MONTH_DAY);
    }

    private SendMessage calendarStepDayNotesMes(Message message){
        SendMessage sendMessage = new SendMessage();
        if(message.getText().matches("^[0-9]+")&&message.getText().length()==2){
            List<Notes> notes =getUserService().getUserNotesByTelegramId(message.getFrom().getId()).getNotes();
            Notes notes1 = notes.get(notes.size()-1);
            DateTime currentTime = new DateTime(notes1.getAlertDate(),DateTimeZone.forID(getUserService().getUserTimeZone(message)));
            DateTime time = new DateTime(currentTime).dayOfMonth().setCopy(checkingUserTime(message.getText()));
            Timestamp alertDateDB = new Timestamp(time.getMillis());
            Integer  hourDay = time.getHourOfDay();
            if(time.getDayOfYear()!=new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message))).getDayOfYear()){
                hourDay=0;
            }
            notes1.setAlertDate(alertDateDB);
            getNotesService().updateNotes(notes1);
            sendMessage.setText("Задайте время Часы");
            sendMessage.disableNotification();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(getHourTimeDateKeyboard(hourDay));
            getUserService().setStepUser(message.getFrom().getId(),NOTES_STEP_HOUR);
        }else {
            sendMessage = notesErrMonth(message);
        }

        return sendMessage;
    }

    private SendMessage todayNotesMes(Message message,int data){
        SendMessage sendMessage = new SendMessage();
        List<Notes> notes =getUserService().getUserNotesByTelegramId(message.getFrom().getId()).getNotes();
        Notes notes1 = notes.get(notes.size()-1);
        DateTime currentTime = new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message)));
        DateTime time = new DateTime(currentTime).plusDays(data);
        Timestamp alertDateDB = new Timestamp(time.getMillis());
        Integer  hourDay = time.getHourOfDay();
        notes1.setAlertDate(alertDateDB);
        getNotesService().updateNotes(notes1);
        sendMessage.setText("Задайте время Часы");
        sendMessage.disableNotification();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);
        if(data>0){
            hourDay = 0;
        }
        sendMessage.setReplyMarkup(getHourTimeDateKeyboard(hourDay));
        getUserService().setStepUser(message.getFrom().getId(),NOTES_STEP_HOUR);
        return sendMessage;
    }

    private SendMessage hourNoteMes(Message message){
        SendMessage sendMessage = new SendMessage();
        if (message.getText().matches("^[0-9]+")&&message.getText().length()==2) {
            List<Notes> notes = getUserService().getUserNotesByTelegramId(message.getFrom().getId()).getNotes();
            Notes notes1 = notes.get(notes.size() - 1);
            DateTime currentTime = new DateTime(notes1.getAlertDate(), DateTimeZone.forID(getUserService().getUserTimeZone(message)));
            String hour = message.getText();
            hour = checkingUserTime(hour);
            DateTime time = new DateTime(currentTime).plusHours(Integer.parseInt(hour) - currentTime.getHourOfDay());
            Timestamp _now = new Timestamp(time.getMillis());
            notes1.setAlertDate(_now);
            getNotesService().updateNotes(notes1);
            sendMessage.setText("Задайте время Минуты");
            sendMessage.disableNotification();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.enableMarkdown(true);
            if (time.getHourOfDay() == new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message))).getHourOfDay()
                    &&time.getDayOfYear() ==new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message))).getDayOfYear()){
                sendMessage.setReplyMarkup(getMinuteTimeDateKeyboard(time.getMinuteOfHour()));
            } else {
                sendMessage.setReplyMarkup(getMinuteTimeDateKeyboard());
            }
            getUserService().setStepUser(message.getFrom().getId(), NOES_FINAL);
        }else {
            sendMessage=todayNotesMes(message,0);
        }
        return sendMessage;
    }
    private SendMessage notesDate(Message message){
        SendMessage sendMessage;
        if(message.getText().equals("Сегодня")){
           sendMessage = todayNotesMes(message,0);
        }else if (message.getText().equals("Завтра")){
            sendMessage = todayNotesMes(message,1);
        }else if(message.getText().equals("Календарь")){
            sendMessage = calendarMesNotes(message);
        }else {
            sendMessage=errorCreateMess(message);
        }
        return sendMessage;
    }
    private SendMessage calendarMesNotes(Message message){
        SendMessage sendMessage = new SendMessage();
        DateTime dateTime = new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message)));
        int month = dateTime.getMonthOfYear();
        sendMessage.setText("Задайте месяц");
        sendMessage.disableNotification();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(getMonthKeyboard(month));
        getUserService().setStepUser(message.getFrom().getId(),NOTES_MONTH);
        return sendMessage;
    }

    private SendMessage notesFinalDate(Message message){
        SendMessage  sendMessage = new SendMessage();
        if (message.getText().matches("^[0-9]+")&&message.getText().length()==2){
            List<Notes> notes =getUserService().getUserNotesByTelegramId(message.getFrom().getId()).getNotes();
            Notes notes1 = notes.get(notes.size()-1);
            DateTime time = new DateTime(notes1.getAlertDate(),DateTimeZone.forID(getUserService().getUserTimeZone(message)));
            String hour = message.getText();
            hour = checkingUserTime(hour);
            int minuts = Integer.parseInt(hour)-time.getMinuteOfHour();
            DateTime test=time.plusMinutes(minuts);
            Timestamp _now = new Timestamp(test.getMillis());
            notes1.setAlertDate(_now);
            getNotesService().updateNotes(notes1);
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.disableNotification();
            sendMessage.setText("Напоминание добавлено");
            sendMessage.setReplyMarkup(getMainMenuKeyboard());
            getUserService().setStepUser(message.getFrom().getId(),MAIN_MENU);
            Long time1 = notes1.getAlertDate().getTime()- new DateTime(DateTimeZone.forID(getUserService().getUserTimeZone(message))).getMillis();
            sendMessageFuture(message.getChatId(),time1,notes1.getId(),message.getFrom().getId());
        }else {
            sendMessage.setText("Задайте время Минуты");
            sendMessage.disableNotification();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(getMinuteTimeDateKeyboard());
            sendMessage.setReplyMarkup(getMinuteTimeDateKeyboard());
        }
        return sendMessage;
    }



    private SendMessage createNotes(Message message){
        SendMessage sendMessage = new SendMessage();
        if(message.hasText()){
            Notes notes = new Notes();
            notes.setUser(getUserService().getUserNotesByTelegramId(message.getFrom().getId()));
            notes.setTextNotes(message.getText());
            notes.setChatId(message.getChatId());
            getNotesService().createNotes(notes);
            sendMessage.setText("Задайте дату");
            sendMessage.disableNotification();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(getDateKeyboard());
            getUserService().setStepUser(message.getFrom().getId(),NOTE_CHOICE_DAY);

        }else {
            sendMessage=createNotes(message);
        }
        return sendMessage;
    }

    private SendMessage errorCreateMess(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Задайте дату");
        sendMessage.disableNotification();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(getDateKeyboard());
        return sendMessage;
    }

    private SendMessage sendStartMsg(Message message,ReplyKeyboardMarkup keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(keyboard);
        sendMessage.disableNotification();
        sendMessage.setText("r");
        getUserService().setStepUser(message.getFrom().getId(),MAIN_MENU);
        return sendMessage;
    }

    private SendMessage sendMainMenuMsg(Message message){
        SendMessage sendMessage;
        if(message.getText().equals("Создать уведомление")){
            sendMessage = sendNotesMsg(message);
        }else if(message.getText().equals("Настройки")){
            sendMessage = getAllNotes(message);
        }else if (message.getText().equals("Все уведомления")) {
            sendMessage = getAllNotes(message);
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
        getUserService().setStepUser(message.getFrom().getId(),CREATION_NOTE);
        return sendMessage;
    }

    private SendMessage getAllNotes(Message message){
        List<Notes> notes =getUserService().getUserNotesByTelegramId(message.getFrom().getId()).getNotes();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(getAllNotesKeyboard(notes));
        sendMessage.disableNotification();
        sendMessage.setText("EEE");
        getUserService().setStepUser(message.getFrom().getId(),GET_ALL_NOTES);
        return sendMessage;
    }

    private SendMessage allNotes(Message message){
        SendMessage sendMessage;
        if(message.getText().equals("Назад")){

            getUserService().setStepUser(message.getFrom().getId(),MAIN_MENU);
            sendMessage=sendStartMsg(message,getMainMenuKeyboard());
        }else {
            sendMessage=getAllNotes(message);
        }
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
