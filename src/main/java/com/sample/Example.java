package com.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Example {

    public static Integer getRandomInteger(){
        int min = 1;
        int max = 100;
        Integer integer = min + (int)(Math.random() * ((max - min) + 1));
        return integer;
    }

    public static void completable() throws Exception {

        CompletableFuture<String> greetingFuture = CompletableFuture
                .supplyAsync(() -> {
            // some async computation
            try {
                Thread.sleep(1000);
                int value = 10/1;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Hello from CompletableFuture";
        })
                .exceptionally((ex) -> "exceptionally error")
                .handle((result, exception) -> {
                    if (exception != null){
                        return "handle error";
                    }else {
                        return result;
                    }
                });

        while (!greetingFuture.isDone()){
            Thread.sleep(500);
            System.out.println("Not Done yet!");
        }

        System.out.println(greetingFuture.get());

        CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> {

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return 100;

                        })
                        .thenApply(x -> x+1);
        System.out.println(future.get());

        CompletableFuture<Integer> future1 =
                CompletableFuture.supplyAsync(() -> 1)
                        .thenCompose(x -> CompletableFuture.supplyAsync(() -> x+1))
                        .thenCompose(x -> CompletableFuture.supplyAsync(() -> x+1));

        System.out.println(future1.get());

    }

    public static void main(String[] args) throws Exception {

        completable();


    }

}

class ThreadPull implements Runnable{

    private Queue queue;
    public ThreadPull(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        queue.remove();

    }
}

class ThreadPush implements Runnable{

    private Queue queue;
    public ThreadPush(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        queue.add();

    }
}

class Queue{

    private List<Integer> queue = new ArrayList<>();

    public synchronized Integer add(){

        while (!canAdd()){
            try {
                Thread.sleep(1000);
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Integer integer = Example.getRandomInteger();
        System.out.println("Added by : " + Thread.currentThread().getId() + " :: " + integer);

        queue.add(integer);
        this.notifyAll();
        return integer;
    }

    public synchronized Integer remove(){

        while (!canRemove()){
            try {
                System.out.println("Waiting: " + Thread.currentThread().getId() + " :: ");
                Thread.sleep(1000);
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Integer integer = queue.remove(0);
        System.out.println("Removed by : " + Thread.currentThread().getId() + " :: " + integer);
        return integer;
    }

    private boolean canAdd(){
        return true;
    }

    private boolean canRemove(){
        return !queue.isEmpty();
    }

}
