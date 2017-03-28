package com.malei.bot.key;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KeyboardAdd {

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
        replyKeyboardMarkup.setOneTimeKeyboad(true);
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
