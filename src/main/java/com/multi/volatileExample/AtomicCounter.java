package com.multi.volatileExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class CounterAtomic extends Thread {

    // Atomic counter Variable
    AtomicInteger count;

    // Constructor of class
    CounterAtomic()
    {
        count = new AtomicInteger();
    }

    // method which would be called upon
    // the start of execution of a thread
    public void run()
    {

        int max = 100;

        // incrementing counter total of max times
        for (int i = 0; i < max; i++) {
            count.addAndGet(1);
        }
    }
}

public class AtomicCounter {
    public static void main(String[] args)
            throws InterruptedException
    {
        long startTime = System.currentTimeMillis();
        // Instance of Counter Class
        CounterAtomic c = new CounterAtomic();

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0 ; i < 50; i++){
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


