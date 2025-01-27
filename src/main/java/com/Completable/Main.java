package com.Completable;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

// just a sample code trying completable

class MySupplier implements Supplier<Integer> {

    @Override
    public Integer get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //Do nothing
        }
        return 1;
    }
}

class PlusOne implements Function<Integer, Integer> {

    @Override
    public Integer apply(Integer x) {
        return x + 1;
    }
}

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return 1;
    }

}

public class Main {

    public static void main(String[] args) throws Exception{
        completableFuture();
    }

    public static void futureOnly() throws Exception{

        ExecutorService exec = Executors.newSingleThreadExecutor();

        Future<Integer> f = exec.submit(new MyCallable());

        System.out.println(f.isDone()); //False

        System.out.println(f.get());
    }

    public static void completableFuture() throws Exception{

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(new MySupplier(), executorService);
        System.out.println(f.isDone()); // False
        CompletableFuture<Integer> f2 = f.thenApply(new PlusOne());
        System.out.println(f2.get());

        executorService.shutdown();
        try {
            while (!executorService.isShutdown()) {
                executorService.shutdownNow();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            executorService.shutdownNow();
        }
    }




    public static void completableSample() throws Exception{

        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> newFuture = future
                .thenApply(s -> s + " World");

        System.out.println(newFuture.get());

    }
}

