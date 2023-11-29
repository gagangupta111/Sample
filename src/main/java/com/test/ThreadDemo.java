package com.test;

class ThreadExample implements Runnable{

    // resource
    // acquire lock , send an integer , release

    private Resource resource;
    private int startNumber;

    public ThreadExample(Resource resource, int startNumber) {
        this.resource = resource;
        this.startNumber = startNumber;
    }

    @Override
    public void run() {



    }
}

class Resource {

    public void print(Integer integer){
        System.out.println(integer);
    }

}

public class ThreadDemo {

    Resource resource = new Resource();
    Thread threadExample1 = new Thread(new ThreadExample(resource, 1));
    Thread threadExample2 = new Thread(new ThreadExample(resource, 2));





}
