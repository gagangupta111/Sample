package com.sample;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

class Multi implements Runnable{

    private CountDownLatch start;
    private CountDownLatch end;

    private int randomInt;

    public Multi(CountDownLatch start, CountDownLatch end, Map<String, Integer> map, int randomInt) {
        this.start = start;
        this.end = end;
        this.randomInt = randomInt;
    }

    @Override
    public void run() {

        try {
            start.await();
            System.out.println(1);
            end.countDown();
        }catch (Exception e){}
    }
}
