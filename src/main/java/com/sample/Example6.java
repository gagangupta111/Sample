package com.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadResource{

    private List<Long> queue = new ArrayList<>();

    public List<Long> getQueue() {
        return queue;
    }
}

class GetterThread implements Runnable{

    private ThreadResource threadResource;

    public GetterThread(ThreadResource threadResource) {
        this.threadResource = threadResource;
    }

    @Override
    public void run() {

        synchronized (threadResource){

            Long aLong = Thread.currentThread().getId();

            while (threadResource.getQueue().isEmpty()){

                try {

                    System.out.println(aLong + " thread : waiting : " );
                    Thread.sleep(1000);
                    threadResource.wait();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }


            Long value = threadResource.getQueue().remove(0);
            System.out.println(aLong + " thread : value : " + value);
            System.out.println(value);

        }

    }
}

class SetterThread implements Runnable{

    private ThreadResource threadResource;

    public SetterThread(ThreadResource threadResource) {
        this.threadResource = threadResource;
    }

    @Override
    public void run() {

        synchronized (threadResource){
            Long aLong = Thread.currentThread().getId();

            threadResource.getQueue().add(aLong);
            threadResource.notifyAll();

        }

    }
}

public class Example6 {

    public static void main(String[] args){

        System.out.println("Hello!");
        ThreadResource resourceQueue = new ThreadResource();
        ExecutorService executorService = Executors.newFixedThreadPool(30);

        for (int i = 0 ; i < 20; i++){
            Thread thread = new Thread(new GetterThread(resourceQueue));
            thread.start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0 ; i < 20; i++){
            Thread thread = new Thread(new SetterThread(resourceQueue));
            thread.start();
        }
    }
}
