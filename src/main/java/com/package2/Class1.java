package com.package2;

import com.package1.Interface1;

public class Class1 implements Interface1 {

    public static void main(String[] args){

        System.out.println("Hello!");
        Class1 class1 = new Class1();

        class1.method1();
        Interface1.method2();
    }
}
