package com.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args){

        System.out.println();

    }

}

class TestClass extends Thread{

    public TestClass(AtomicInteger x) {
        this.x = x;
    }

    private volatile static AtomicInteger x;

    public void print(){
        System.out.println(x);
    }

    public void inc(){
        x.getAndIncrement();
    }

    @Override
    public synchronized void run(){
        inc();
        print();
    }

}

class ThreadClass implements Runnable {

    public static volatile int number;

    public ThreadClass(int number) {
        this.number = number;
    }

    @Override
    public synchronized void run() {

        System.out.println(Thread.currentThread().getId() + " : " + number++);

    }
}
