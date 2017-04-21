package com.malei.bot.utilities;


import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.api.objects.Message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.google.maps.TimeZoneApi.getTimeZone;


public class Utilities {
    public static String checkingUserTime(String time){

        if(Character.toString(time.charAt(0)).equals("0")){
            time=Character.toString(time.charAt(1));
        }
        return time;
    }

    public static String getTimeZoneLocation(Message message){
        String s = null;
        try {
            s = getTimeZone(new GeoApiContext().setApiKey("AIzaSyDUm0FF-A1_2KBOnseWXixVvPIaV4Is-KQ"),
                    new LatLng(message.getLocation().getLatitude(),message.getLocation().getLongitude()))
                    .await().toZoneId().toString();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        return s;
    }

    public static int monthToInt(String month){
        int intMonth = 0;
        switch (month){
            case("Январь"):
                intMonth = 1;
                break;
            case("Февраль"):
                intMonth = 2;
                break;
            case("Март"):
                intMonth = 3;
                break;
            case("Апрель"):
                intMonth = 4;
                break;
            case("Май"):
                intMonth = 5;
                break;
            case("Июнь"):
                intMonth = 6;
                break;
            case("Июль"):
                intMonth = 7;
                break;
            case("Август"):
                intMonth = 8;
                break;
            case("Сентябрь"):
                intMonth = 9;
                break;
            case("Октябрь"):
                intMonth = 10;
                break;
            case("Ноябрь"):
                intMonth = 11;
                break;
            case("Декабрь"):
                intMonth = 12;
                break;
        }
        return intMonth;
    }
    public static String intToMonth(int month){
        String stringMonth = null;
        switch (month){
            case(1):
                stringMonth = "Январь";
                break;
            case(2):
                stringMonth = "Февраль";
                break;
            case(3):
                stringMonth = "Март";
                break;
            case(4):
                stringMonth = "Апрель";
                break;
            case(5):
                stringMonth = "Май";
                break;
            case(6):
                stringMonth = "Июнь";
                break;
            case(7):
                stringMonth = "Июль";
                break;
            case(8):
                stringMonth = "Август";
                break;
            case(9):
                stringMonth = "Сентябрь";
                break;
            case(10):
                stringMonth = "Октябрь";
                break;
            case(11):
                stringMonth = "Ноябрь";
                break;
            case(12):
                stringMonth = "Декабрь";
                break;
        }
        return stringMonth;
    }

    public static String local(String key, String local) throws UnsupportedEncodingException {
        Locale locale = new Locale(local);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("localisation.message", locale);
        String s = resourceBundle.getString(key);
        String s1 = new String(s.getBytes("ISO-8859-1"), "UTF-8");

        return new StringBuilder().append(s1).toString();

    }
}
