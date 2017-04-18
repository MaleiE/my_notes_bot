package com.malei.bot.key;

import com.malei.bot.entities.Notes;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KeyboardAdd {

    public static ReplyKeyboardMarkup getLocKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow0 = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton("Отправить место положения");
        keyboardButton.setRequestLocation(true);
        keyboardRow0.add(keyboardButton);
        keyboard.add(keyboardRow0);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getMainMenuKeyboard() {
        //ReplyKeyboardMarkup
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        //KeyboardRow
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardRow0 = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        keyboardRow0.add(new KeyboardButton("Создать уведомление"));
        keyboardRow1.add(new KeyboardButton("Настройки"));
        keyboardRow2.add(new KeyboardButton("Все уведомления"));

        keyboard.add(keyboardRow0);
        keyboard.add(keyboardRow1);
        keyboard.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
    public static ReplyKeyboardMarkup getDateKeyboard() {
        //ReplyKeyboardMarkup
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        //KeyboardRow
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardRow0 = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        keyboardRow0.add(new KeyboardButton("Сегодня"));
        keyboardRow1.add(new KeyboardButton("Завтра"));
        keyboardRow2.add(new KeyboardButton("Календарь"));

        keyboard.add(keyboardRow0);
        keyboard.add(keyboardRow1);
        keyboard.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getHourTimeDateKeyboard(Integer hour) {
        //ReplyKeyboardMarkup
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        System.out.println(hour);
        //KeyboardRow
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>(){{
            for (int i = hour;i<24;i++){
                KeyboardRow keyboardRow = new KeyboardRow();
                String hour = Integer.toString(i);
                if(i<10){
                    hour="0"+hour;
                }
                keyboardRow.add(new KeyboardButton(hour));
                add(keyboardRow);
            }
        }};
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
    public static ReplyKeyboardMarkup getHourTimeDateKeyboard() {
        //ReplyKeyboardMarkup
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        //KeyboardRow
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>(){{
            for (int i = 0;i<24;i++){
                KeyboardRow keyboardRow = new KeyboardRow();
                String hour = Integer.toString(i);
                if(i<10){
                    hour="0"+hour;
                }
                keyboardRow.add(new KeyboardButton(hour));
                add(keyboardRow);
            }
        }};
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getMinuteTimeDateKeyboard() {
        //ReplyKeyboardMarkup
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        //KeyboardRow
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>(){{
            for (int i = 0;i<60;i++){
                KeyboardRow keyboardRow = new KeyboardRow();
                String hour = Integer.toString(i);
                if(i<10){
                    hour="0"+hour;
                }
                keyboardRow.add(new KeyboardButton(hour));
                add(keyboardRow);
            }
        }};
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getMinuteTimeDateKeyboard(Integer minute) {
        //ReplyKeyboardMarkup
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        //KeyboardRow
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>(){{
            for (int i = minute;i<60;i++){
                KeyboardRow keyboardRow = new KeyboardRow();
                String hour = Integer.toString(i);
                if(i<10){
                    hour="0"+hour;
                }
                keyboardRow.add(new KeyboardButton(hour));
                add(keyboardRow);
            }
        }};
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getAllNotesKeyboard(List<Notes> notes) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        //KeyboardRow
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>(){{
            for (Notes note : notes) {
                KeyboardRow keyboardRow = new KeyboardRow();
                keyboardRow.add(new KeyboardButton(note.getTextNotes()));
                add(keyboardRow);
            }
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton("Назад"));
            add(keyboardRow);

        }};

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getMonthKeyboard(int month) {
        String[] months ={"Январь","Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь",
                "Январь","Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>(){{
            for (int i = month-1;i<12+month-2;i++){
                KeyboardRow keyboardRow = new KeyboardRow();
                keyboardRow.add(new KeyboardButton(months[i]));
                add(keyboardRow);
            }
        }};
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getDayMonthKeyboard(Integer day,Integer dayMonth) {
        //ReplyKeyboardMarkup
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        replyKeyboardMarkup.setOneTimeKeyboad(false);
        //KeyboardRow
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>(){{
            for (int i = day;i<dayMonth+1;i++){
                KeyboardRow keyboardRow = new KeyboardRow();
                String hour = Integer.toString(i);
                if(i<10){
                    hour="0"+hour;
                }
                keyboardRow.add(new KeyboardButton(hour));
                add(keyboardRow);
            }
        }};
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }



    private static String getPropertiesKey(String nameProp){
        Properties property = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/test.properties");
            property.load(fis);
            return property.getProperty(nameProp);
        } catch (IOException e) {
            System.err.println("Ошибочка"+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
