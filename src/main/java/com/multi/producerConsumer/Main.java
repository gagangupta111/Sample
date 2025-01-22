package com.multi.producerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ResourceClass {

    public List<String> strings = new ArrayList<>();

}

class Producer extends Thread{

    private ResourceClass resourceClass;
    private String name;

    public Producer(ResourceClass resourceClass, String name) {
        this.resourceClass = resourceClass;
        this.name = name;
    }

    public void run(){

        while (true){
        synchronized (resourceClass){
                while (resourceClass.strings.size() > 30){
                    try {
                        resourceClass.wait();
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Exception : " + e.getMessage());
                    }
                }
                Random random = new Random();
                Integer integer = random.nextInt(1000);
                resourceClass.strings.add(integer + "");
                System.out.println("Producer: " + name + " " + integer);

                try {
                    resourceClass.notifyAll();
                } catch (Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                }
            }
        }
    }

}

class Consumer extends Thread{

    private ResourceClass resourceClass;
    private String name;

    public Consumer(ResourceClass resourceClass, String name) {
        this.resourceClass = resourceClass;
        this.name = name;
    }

    public void run(){
        while (true){
        synchronized (resourceClass){
                while (resourceClass.strings.isEmpty()){
                    try {
                        resourceClass.wait();
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Exception : " + e.getMessage());
                    }
                }

                String s = resourceClass.strings.remove(0);
                System.out.println("Consumer: " + name + " " + s);

                try {
                    resourceClass.notifyAll();
                } catch (Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                }
            }
        }
    }
}

public class Main {

    public static void main(String[] args){

        runAll();

    }

    public static void runAll(){

        ResourceClass resourceClass = new ResourceClass();
        ExecutorService executorService = Executors
                .newFixedThreadPool(10);
        for (int i = 0 ; i < 2; i++){
            executorService.submit(new Producer(resourceClass, i+""));
            executorService.submit(new Consumer(resourceClass, i+""));
        }
    }
}
