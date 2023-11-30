package com.sample;

import java.io.*;

public class SerializableExample {

    public static void main(String[] args){

        try {
            sample();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void sample() throws Exception {

        Person person = new Person();
        person.setAge(20);
        person.setName("Joe");

        FileOutputStream fileOutputStream
                = new FileOutputStream("yourfile.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(person);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream
                = new FileInputStream("yourfile.txt");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        Person p2 = (Person) objectInputStream.readObject();
        objectInputStream.close();

        System.out.println(p2.getAge() + "__" + person.getAge());
        System.out.println(p2.getName() + "__" + person.getName());
    }

}
