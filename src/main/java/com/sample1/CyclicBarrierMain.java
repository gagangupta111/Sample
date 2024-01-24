package com.sample1;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ThreadExample implements Runnable{

    private CyclicBarrier cyclicBarrier;

    public ThreadExample(CyclicBarrier cyclicBarrierMain) {
        this.cyclicBarrier = cyclicBarrierMain;
    }

    @Override
    public void run() {

        try {

            Long ID = Thread.currentThread().getId();
            System.out.println("Thread: " + ID + "Started ");

            Thread.sleep(1000);
            System.out.println("Thread: " + ID + "__" + cyclicBarrier.getNumberWaiting());

            Thread.sleep(1000);

            System.out.println("Thread: " + ID + "__ awaiting ");

            cyclicBarrier.await(5, TimeUnit.SECONDS);

            System.out.println("Thread: " + ID + "__" + "Done ");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}

public class CyclicBarrierMain {

    public static void main(String[] args) throws Exception{

        int size = 5;

        System.out.println("Hello!");
        CyclicBarrier cyclicBarrier = new CyclicBarrier(size, () -> {
            System.out.println("All Done!");
        });

        ExecutorService executorService = Executors.newFixedThreadPool(size);

        for (int i = 0 ; i < size; i++){
            executorService.submit(new Thread(new ThreadExample(cyclicBarrier)));
        }
        Thread.sleep(100);

        for (int i = 0 ; i < size; i++){
            executorService.submit(new Thread(new ThreadExample(cyclicBarrier)));
        }

    }
}
