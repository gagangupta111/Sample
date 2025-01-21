package com.multi.threadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 class MyRunnable implements Runnable {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    @Override
    public void run() {
        Long aLong = Thread.currentThread().getId();
        threadLocal.set( (int) (Math.random() * 100D) );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        System.out.println(" ID:  " + aLong + " thread local : " +threadLocal.get());
    }
}

public class ThreadLocalExample {
    public static void main(String[] args)
            throws InterruptedException
    {
        long startTime = System.currentTimeMillis();
        int total = 50;

        ExecutorService executorService = Executors.newFixedThreadPool(total);
        for (int i = 0 ; i < total; i++){
            executorService.submit(new Thread(new MyRunnable()));
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

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("elapsedTime : " + elapsedTime);
    }
}
