/*

package com.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

class BuilderClass{

    private String name;

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static class Builder{

        private String name;

        public static Builder newInstance(){

            return new Builder();

        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public BuilderClass build(){
            BuilderClass builderClass = new BuilderClass();
            builderClass.setName(this.name);
            return builderClass;
        }

    }

}
public class Example1 {

    public static void builder(){

        BuilderClass builderClass = BuilderClass.Builder.newInstance().withName("gagan").build();
        System.out.println(builderClass.getName());

    }

    public static void completableFuture(){

        CompletableFuture<Void> returnValue = CompletableFuture.runAsync(() -> {

            try {
                Thread.sleep(1000);
                int value = 10/0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Inside 1 : ");
            return;
        });
        try {
            returnValue.get();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        CompletableFuture<String> returnedValue = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(1000);
                int value = 10/1;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Inside 2 : ");
            return "Inside 2";
        })
                .handle((a,b) -> {
                    if (b != null){
                        System.out.println("inside 4 : " + b.getLocalizedMessage());
                        return "inside 4 " + b.getLocalizedMessage();
                    }
                    System.out.println("inside 4 : " + a);
                    return "inside 4 " + a;
                })
                .exceptionally((throwable) -> {
                    System.out.println("inside 3 : " + throwable.getLocalizedMessage());
                    return "inside 3 : " + throwable.getLocalizedMessage();
                });

        try {
            returnedValue.get();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    public static void multiThread(){

        ResourceClass resource =  new ResourceClass();
        for (int i = 0; i < 10; i++){
            Thread thread = new Thread(new GetResource(resource));
            thread.start();
        }

        for (int i = 0; i < 10; i++){
            Thread thread = new Thread(new SetResource(resource));
            thread.start();
        }

    }

    public static void main(String[] args){

        builder();
    }
}

class SetResource implements Runnable{

    private ResourceClass resource;

    public SetResource(ResourceClass resource) {
        this.resource = resource;
    }

    @Override
    public void run() {

        synchronized (resource){
            while (resource.queue.size() > 2){
                try {
                    System.out.println("Thread : " + Thread.currentThread().getId() + " set waiting ");
                    Thread.sleep(500);
                    resource.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
        resource.set();
    }
}

class GetResource implements Runnable{

    private ResourceClass resource;

    public GetResource(ResourceClass resource) {
        this.resource = resource;
    }

    @Override
    public void run() {

        synchronized (resource){
            while (resource.queue.isEmpty()){
                try {
                    System.out.println("Thread : " + Thread.currentThread().getId() + " get waiting ");
                    Thread.sleep(500);
                    resource.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
        resource.get();
    }
}

class Resource{

    public List<Integer> queue = new ArrayList<>();

    public synchronized void get(){

        System.out.println("Thread : " + Thread.currentThread().getId() + " get waiting done");
        System.out.println("Thread : " + Thread.currentThread().getId() + " :: " + queue.remove(0));

    }

    public synchronized void set(){

        System.out.println("Thread : " + Thread.currentThread().getId() + " set waiting done");
        Random r = new Random();
        int low = 10;
        int high = 100;
        int result = r.nextInt(high-low) + low;
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        queue.add(result);
        this.notifyAll();

    }
}
*/
