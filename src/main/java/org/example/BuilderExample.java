
package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class StrategyDemo{
    Strategy strategy;

    public StrategyDemo(Strategy strategy) {
        this.strategy = strategy;
    }

    public int operation(int a, int b){
        return strategy.operation(a,b);
    }

}

interface Strategy{

    public int operation(int a, int b);

}

class Addition implements Strategy{

    @Override
    public int operation(int a, int b) {
        return a+b;
    }
}

class Multiply implements Strategy{

    @Override
    public int operation(int a, int b) {
        return a*b;
    }
}



class Fan{

    State state;

    public Fan(){
        state = new OffState("Offstate");
    }

    public void pull(){
        state.pull(this);
    }

}

interface State{
    public void pull(Fan fan);
}

class OffState implements State{

    String name;

    public OffState(String name) {
        this.name = name;
    }

    @Override
    public void pull(Fan fan) {
        fan.state = new OnState("OnState");
    }

    @Override
    public String toString() {
        return "OffState{" +
                "name='" + name + '\'' +
                '}';
    }
}

class OnState implements State{

    String name;

    public OnState(String name) {
        this.name = name;
    }
    @Override
    public void pull(Fan fan) {
        fan.state = new OffState("OffState");
    }

    @Override
    public String toString() {
        return "OnState{" +
                "name='" + name + '\'' +
                '}';
    }
}


interface A{

    static void printC(){
        System.out.println("C");
    }

    default void printA(){
        System.out.println("A");
    }

    static void printB(){
        System.out.println("B");
    }

}

class AA implements A{

}

class ThreadExample implements Runnable{

    public void run(){
        for (int i = 0 ; i < 10; i++){
            System.out.println("Inside :: " + Thread.currentThread().getId());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Thread.yield();
        }
    }

}

public class BuilderExample {

    public static void main(String[] args){

        System.out.println("Hello!");

    }
}

class Singleton{

    private Singleton(){}

    private static class Inner{
        private static final Singleton SINGLETON = new Singleton();
    }

    public static Singleton getInstance(){
        return Inner.SINGLETON;
    }

}

class Home{

    private String name;

    public String getName(){
        return name;
    }

    public static class Builder{

        private String name;

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Home build(){
            Home home = new Home();
            home.name = this.name;
            return home;
        }

    }

}