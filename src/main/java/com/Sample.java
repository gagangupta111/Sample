package com;

import java.util.HashMap;
import java.util.Map;

public class Sample {

    public static void swap(int[] array, int begIndex, int endIndex){

        int temp = array[begIndex];
        array[begIndex] = array[endIndex];
        array[endIndex] = temp;
    }

    public static void sort(int[] array){
        {

            int length = array.length;
            int pastIndex = -1;
            for (int i = 0 ; i < length; i++){
                if (array[i] == 1){
                    pastIndex = i;
                    break;
                }
            }

            if (pastIndex == -1 || pastIndex == length-1){
                return;
            }

            for (int i = pastIndex; i < length; i++){
                if (array[i] == 0){
                    swap(array, pastIndex, i);
                    pastIndex++;
                }
            }
        }
    }

    public static void main(String[] args){


        String string = "abcabca";
        Map<Character, Integer> map = new HashMap<>();
        int length = string.length();

        for (int i = 0 ; i < length; i++){
            Character c = string.charAt(i);
            if (!map.containsKey(c)){
                map.put(c, 1);
            }else {
                map.put(c, map.get(c)+1);
            }
        }

        for (Character c : map.keySet()){
            System.out.println(c + " ___ " + map.get(c));
        }



    }
}
