package com.example.sanchell239.rxjavasample;

import java.util.Random;


public class LongOp {
    private String url = null;

    public LongOp(String myUrl) {
        url = myUrl;
    }

    public String getResult() throws Exception {
        Random ran = new Random();
        int sleepTime = ran.nextInt(2001) + 1000;

        try {
            Thread.currentThread().sleep(sleepTime);
            if (sleepTime % 5 != 0) {
                return "поток: " + Thread.currentThread().getName() + ", success loading from url: " + url + ", время загрузки: " + sleepTime;
            }
            else {
                throw new Exception("поток: " + Thread.currentThread().getName() + ", что-то пошло не так при загрузке с url: " + url + ", время загрузки " + sleepTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
