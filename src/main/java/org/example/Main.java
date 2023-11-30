
package org.example;

import java.util.*;

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

class Employee{

    public Employee(int age, String type) {

        this.age = age;
        this.type = type;

    }

    public int age;
    public String type;

    @Override
    public String toString() {
        return "age:" + age + " type: " + type;
    }
}



public class Main {

    public static Integer getRecentMaxRepeatedValue(List<Integer> list){

        Integer maxKey = -1;
        Integer maxvalue = -1;
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer integer : list){

            if (!map.containsKey(integer)){
                map.put(integer, 1);
            }else {
                map.put(integer, map.get(integer) + 1);
            }

            if (map.get(integer) >= maxvalue){
                maxvalue = map.get(integer);
                maxKey = integer;
            }

        }

        return maxKey;
    }

    public static void main(String[] args) {

        // 1,2,3,2,2,3,4,4,5,5 =  2?
        // maxKey = 4, maxValue = 2
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(4);
        list.add(5);
        list.add(5);


        System.out.println(getRecentMaxRepeatedValue(list));

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
