
package com.sample;

import java.util.ArrayList;
import java.util.List;

class Row{

    public Row(Integer empID, Integer deptID, Integer salary) {
        this.empID = empID;
        this.deptID = deptID;
        this.salary = salary;
    }

    public Integer empID;
    public Integer deptID;
    public Integer salary;


}

public class Example5 {

    public static void main(String[] args){

        List<String> list = allPossible("abcd");
        System.out.println(list.size());
        for (String string : list){
            System.out.println(string);
        }
    }

    public static List<String> allPossible(String s){

        List<String> list = new ArrayList<>();
        int length = s.length();

        if (s.length() == 1){
            list.add(s);
            return list;
        }

        for (int i = 0; i < length; i++){

            String first = s.charAt(i) + "";
            String rest = "";
            if (i == 0){
                rest = s.substring(i+1, length);
            }else {
                rest += s.substring(0,i);
                rest += s.substring(i+1, length);
            }
            List<String> responses = allPossible(rest);
            for (String string : responses){
                list.add(first + string);
            }
        }
        return list;
    }
}
