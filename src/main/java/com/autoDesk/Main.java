package com.autoDesk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){

        System.out.println(returnMaxEven(List.of(1,3,5)));

    }

    public static int returnMaxEven(List<Integer> list){

        int maxKey = -1;
        int maxValue = Integer.MIN_VALUE;

        Map<Integer, Integer> map = new HashMap<>();

        for (Integer key : list){

            if (key % 2 != 0){
                continue;
            }

            Integer value = map.get(key);
            if (value == null){
                value = 1;
            }else {
                value++;
            }
            map.put(key, value);

            if (value > maxValue){
                maxValue = value;
                maxKey = key;
            } else if (value == maxValue){
                if (key < maxKey){
                    maxKey = key;
                }
            }

        }

        return maxKey;
    }

}
