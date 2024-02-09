package com.designpatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

interface Vehicle{

    public void drive();

    public static void fillFuel(){
        System.out.println("Fuel Filled");
    }

    public default void paint(){
        System.out.println("Painted");
    }

}

class Car implements Vehicle{

    @Override
    public void drive() {
        System.out.println("Drive");
    }

}

class Factory{

    public Vehicle getVehicle(String vehicle){
        if (vehicle.equals("car")){
            return new Car();
        }
        else return new Car();
    }

}

public class Main {

    public static void main(String[] args){

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0 ; i < 10; i++){
            executorService.submit(() -> {
                SingletonInnerClass singleton = SingletonInnerClass.getSingleton();
                System.out.println(singleton);
            });
        }
    }
}

 class FinalClass{

    private final int value;

    public FinalClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class FinalChildClass extends FinalClass{

    public FinalChildClass(int value) {
        super(value);
    }
}

class Employee{

    public int salary;
    public String name;

    public Employee(int salary, String name) {
        this.salary = salary;
        this.name = name;
    }
}

class SingletonInnerClass{

    private SingletonInnerClass(){

    }

    private static class SingletonInnerClass1{
        private static SingletonInnerClass singletonInnerClass = new SingletonInnerClass();
    }

    public static SingletonInnerClass getSingleton(){
        return SingletonInnerClass1.singletonInnerClass;
    }
}

class Singleton{

    private static Singleton singleton;

    private Singleton(){

    }

    public static Singleton getSingleton(){

        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
