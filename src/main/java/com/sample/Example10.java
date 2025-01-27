package com.sample;

import java.util.*;

public class Example10 {

    // apple  cat tac act camel lemca lcame : apple : 1, cat : 3, camel : 3
    // Given a paragraph of text, return the count of each word where words with same characters must be considered same word, Eg - cat and act must be considered 1 word not 2 separate words
    public static String existsInMap(Map<String, Integer> map, String string){

        for (String s : map.keySet()){
            if (isItMatch(s, string)){
                return s;
            }
        }
        return null;
    }

    public static boolean isItMatch(String input, String target){

        char[] array = input.toCharArray();
        Arrays.sort(array);

        char[] array1 = target.toCharArray();
        Arrays.sort(array1);

        if (String.valueOf(array).equals(String.valueOf(array1))){
            return true;
        }else
        {
            return false;
        }
    }

    public static Map<String, Integer> getAllCounts(String line){

        Map<String, Integer> map = new HashMap<>();
        String[] splits = line.split(" ");

        for (String string : splits){

            String returnValue = existsInMap(map, string);
            if (returnValue == null){
                map.put(string, 1);
            }else {
                map.put(returnValue, map.get(returnValue)+1);
            }
        }
        return map;
    }

    public static void main(String[] args){


        HashMap<String,String> map = new HashMap<String,String>(){
            {
                put("1", "ONE");
                put("2", "TWO");
                put("3", "THREE");
            }
        };
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        Collection<String> unmodifiableCollection = Collections.unmodifiableCollection(list);
        list.add("4");

        for (String string : unmodifiableCollection){
            System.out.println(string);
        }

        List<Integer> integerList = List.of(1,2,3);
        integerList.add(4);

    }

}
