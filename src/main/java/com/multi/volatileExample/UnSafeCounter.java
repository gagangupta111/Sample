package com.multi.volatileExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CounterUnsafe extends Thread {

    // Counter Variable
    int count = 0;

    // method which would be called upon
    // the start of execution of a thread
    public void run()
    {
        int max = 100;

        // incrementing counter
        // total of max times
        for (int i = 0; i < max; i++) {
            count++;
        }
    }
}

public class UnSafeCounter {
    public static void main(String[] args)
            throws InterruptedException
    {
        long startTime = System.currentTimeMillis();
        // Instance of Counter Class
        CounterUnsafe c = new CounterUnsafe();

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0 ; i < 20; i++){
            executorService.submit(new Thread(c));
        }

        executorService.shutdown();
        try {
            while (!executorService.isShutdown()) {
                executorService.shutdownNow();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            executorService.shutdownNow();
        }

        // Printing final value of count variable
        System.out.println("count : " + c.count);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("elapsedTime : " + elapsedTime);
    }
}
