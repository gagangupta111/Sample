package com.Completable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/*
    below code demonstrates how CompletableFuture can also work similar to Thread/Runnable,
    by creating Supplier instead of Thread/Runnable objects.

    below code use ExecutorService to manage lifecycle of threads,
    Supplier to create threads, CountDownLatch to control execution of threads.

*/

public class CounterWithCompletable {
    public static int increments = 100;
    public static void main(String[] args)
            throws InterruptedException
    {
        int threads = 100;

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(threads);

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        List<CompletableFuture<Integer>> list = new ArrayList<>();
        for (int i = 0 ; i < threads; i++){
            list.add(CompletableFuture.supplyAsync(new CounterSupplier(start, end), executorService));
        }

        // delaying the start of threads
        Thread.sleep(5000);

        // count down started
        start.countDown();

        boolean flag = false;
        // loop to check if all threads are completed, this can be done by checking the countdown latch as well
        // but checking the thread completion directly is more accurate
        while (!flag){
            flag = true;
            for (CompletableFuture<Integer> future : list){
                flag = flag & future.isDone();
            }
            System.out.println("flag : " + flag);
            Thread.sleep(1000);
        }

        // all threads are done, hence printing
        System.out.println("Counter value : " + CounterSupplier.count);

        // loop to shut down executorService
        executorService.shutdown();
        try {
            while (!executorService.isShutdown()) {
                executorService.shutdownNow();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            executorService.shutdownNow();
        }
    }
}

class CounterSupplier implements Supplier<Integer> {


    CountDownLatch start;
    CountDownLatch end;

    public CounterSupplier(CountDownLatch start, CountDownLatch end) {
        this.start = start;
        this.end = end;
    }

    public static AtomicInteger count = new AtomicInteger(0);

    @Override
    public Integer get() {

        try {
            String id = Thread.currentThread().getName();
            System.out.println(id + " waiting to start ");
            start.await();
            // incrementing counter
            for (int i = 0; i < CounterWithCompletable.increments; i++) {
                count.getAndIncrement();
            }
            Thread.sleep(5000);
            end.countDown();
        } catch (Exception e) {}
        // just returning, no business logic
        return 1;
    }
}