package com.malei.bot;

import org.joda.time.LocalDateTime;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class r {
    public static void main(String args[]) {
        Timer timer = new Timer("potok 1");
        timer.schedule(new SayHello("dwdw"), 4000 );

        Timer timer1 = new Timer("potok2");
        timer1.schedule(new SayHello("100101012002"),1000);

        //ScheduledExecutorService  !!!!ПРИМЕНИТЬ 


        //if(timer.g)
     //   timer.cancel();
        //timer.toString();// ставим по расписанию выполнять SayHello каждые 4 секунды
    }
}

class SayHello extends TimerTask {
    private  String text;
    private String id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    SayHello() {
    }

    SayHello(String text) {
        this.text = text;
    }

    public void run() {
        System.out.println("Timer Name: "
                + Thread.currentThread().getName()+text);
        System.out.println(Thread.currentThread().getId());
        Long l =Thread.currentThread().getId();
        id=l.toString();
    }
}
/*
public class r {
    public static void main(String[ ] args) {
        System.out.println("frefer");
        new Thread(() -> {
            System.out.println("старт потока 1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("завершение 1");
        }).start();
        new Thread(() -> {
            System.out.println("старт потока 2");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("завершение 2");
        }).start();
        System.out.println("cewfwef");
    }
}*/