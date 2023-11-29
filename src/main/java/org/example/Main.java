
package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class EqualsHashCode{

    public String name;
    public int id;

    public EqualsHashCode(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EqualsHashCode that = (EqualsHashCode) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}

public class Main {

    public static void main(String[] args){



    }
}

class Adder implements Runnable{

    Queue queue;
    public Adder(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        System.out.println("Before Adder " + Thread.currentThread().getId());
        Random randomNum = new Random();
        int showMe = randomNum. nextInt(100);
        queue.put(showMe);
        notify();
        System.out.println("After Adder " + Thread.currentThread().getId());

    }
}

class Remover implements Runnable{

    Queue queue;
    public Remover(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        System.out.println("Before Remover " + Thread.currentThread().getId());
        Integer integer = queue.get();
        System.out.println("After Remover " + Thread.currentThread().getId() + " ++ " + integer);

    }
}


class Queue{

    private List<Integer> queue = new ArrayList<>();

    public synchronized Integer get(){

        while (queue.isEmpty()){
            try {
                System.out.println("Waiting get " + Thread.currentThread().getId());
                Thread.sleep(1000);
                wait();
                System.out.println("Out of Waiting get " + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Integer integer = queue.get(0);
        return queue.remove(0);
    }

    public synchronized void put(Integer value){

        while (queue.size() > 5){
            try {
                System.out.println("Waiting put " + Thread.currentThread().getId());
                Thread.sleep(1000);
                wait();
                System.out.println("Out of Waiting put " + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.add(value);

    }
}
