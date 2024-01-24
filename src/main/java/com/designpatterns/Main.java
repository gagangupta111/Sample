package com.designpatterns;

import java.util.ArrayList;
import java.util.List;

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

        System.out.println("Hello!");

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
