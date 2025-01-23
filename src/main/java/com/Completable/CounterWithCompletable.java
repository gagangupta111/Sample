package com.Completable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class CounterWithCompletable {
    public static void main(String[] args)
            throws InterruptedException
    {
        int threads = 100;
        CounterSupplier.max = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        List<CompletableFuture<Integer>> list = new ArrayList<>();
        for (int i = 0 ; i < threads; i++){
            list.add(CompletableFuture.supplyAsync(new CounterSupplier(), executorService));
        }

        Thread.sleep(1000);
        boolean flag = false;
        while (!flag){
            flag = true;
            for (CompletableFuture<Integer> future : list){
                flag = flag & future.isDone();
            }
            System.out.println("flag : " + flag);
            Thread.sleep(1000);
        }

        System.out.println("Counter value : " + CounterSupplier.count);

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

    public static AtomicInteger count = new AtomicInteger(0);
    public static int max = 100;

    @Override
    public Integer get() {
        try {

            // incrementing counter
            // total of max times
            for (int i = 0; i < max; i++) {
                count.getAndIncrement();
            }
            Thread.sleep(5000);
        } catch (Exception e) {
            //Do nothing
        }
        return 1;
    }
}