package com.happy;

import java.util.*;

// https://en.wikipedia.org/wiki/Happy_number
public class Main {

    public static void main(String[] args){

        System.out.println(isHappy(4, new HashSet<>()));
        System.out.println(isHappy(5, new HashSet<>()));
        System.out.println(isHappy(6, new HashSet<>()));
        System.out.println(isHappy(11, new HashSet<>()));
        System.out.println(isHappy(12, new HashSet<>()));
        System.out.println(isHappy(15, new HashSet<>()));


    }

    public static boolean isHappy(int number, Set<Integer> integers){

        if (number == 1){
            return true;
        }
        List<Integer> list = new ArrayList<>();
        while ( number >= 10){
            int remainder = number%10;
            if (remainder != 0){
                list.add(remainder);
            }
            number = number / 10;
        }
        if (number != 0){
            list.add(number);
        }

        int afterSumOfSquares = 0;
        for (int num : list){
            afterSumOfSquares += num*num;
        }
        if (integers.contains(afterSumOfSquares)){
            return false;
        }

        integers.add(afterSumOfSquares);
        return isHappy(afterSumOfSquares, integers);

    }

}
