package com.sample;

import com.kaprekar.Main;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.*;

public class Example6 {

    public static int nonDivisibleSubset(int k, List<Integer> s) {
        // Write your code here

        List<Integer> newList = new ArrayList<>();

        int length = s.size();
        for(int i = 0 ; i < length; i++){
            int first = s.get(i);
            for(int j = i+1 ; j < length; j++){
                int second = s.get(j);
                if((first + second) % k != 0){
                    if (!newList.contains(first)){
                        newList.add(first);
                    }
                    if (!newList.contains(second)){
                        newList.add(second);
                    }
                }
            }
        }
        if (newList.size() == length){
            return length;
        }
        return nonDivisibleSubset(k, newList);
    }

    public static int isConsecutive(String s, int length, int i, int yesTillLength) {

        int startIndex = s.charAt(i);
        int endIndex = s.charAt(i+1);

        if (yesTillLength==0){
            if (startIndex != endIndex){
                return yesTillLength+1;
            }else return yesTillLength;
        }else {
            int j = i+1+yesTillLength;
            i = i-yesTillLength;
            while (i > -1 && j < length){
                if (s.charAt(i) == startIndex && s.charAt(j) == endIndex){
                    j = j+1;
                    i = i-1;
                    yesTillLength++;
                }else {
                    return yesTillLength;
                }
            }
            return yesTillLength;
        }
    }

        public static int countBinarySubstrings(String s) {

        s = "00110011";
        int count = 0;
        // 01 0011 10 1100 01 0011

        // go till we find 0,1 together,
        // now expand both sides till 0,1 stay together or end of length
        // once done move ahead and repeat till end of length

        int length = s.length();
        for (int i = 0; i < length-1;){

            int yesTillLength = 0;
            int isConsecutiveLength = 0;
            int newIsConsecutiveLength = isConsecutive(s,length,i,yesTillLength);
            if ( newIsConsecutiveLength > isConsecutiveLength ){
                count = count + 1;
                newIsConsecutiveLength = isConsecutive(s,length,i,1);
                if (newIsConsecutiveLength > 1){
                    count = newIsConsecutiveLength;
                }
            }

        }
        return 0;
    }

    public static void oppositeExists(List<String> allValues, String current, String next) {

        int length = allValues.size();
        int index = -1;

        for (int i = 0 ; i < length; i++){
            if (allValues.get(i).equals(next)){
                index = i;
                break;
            }
        }
        if (index != -1){
            allValues.remove(index);
        }else {
            allValues.add(current);
        }
    }

    public static boolean judgeCircle(String moves) {

        Map<String, String> map = new HashMap<>();
        map.put("L", "R");
        map.put("R", "L");
        map.put("U", "D");
        map.put("D", "U");

        List<String> allValues = new ArrayList<>();
        int length = moves.length();

        for (int i = 0; i < length; i++){
            String current = moves.substring(i, i+1);
            oppositeExists(allValues, current, map.get(current));
        }
        return allValues.size() == 0;
    }

    public static String reverseVowels(String string) {

        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');

        set.add('A');
        set.add('E');
        set.add('I');
        set.add('O');
        set.add('U');

        char[] s = string.toCharArray();
        int length = s.length;
        int i = 0 ;
        int j = length-1;

        while (true){

            while (i < length){
                if (set.contains(s[i])){
                    break;
                }
                i++;
            }

            while (j > -1){
                if (set.contains(s[j])){
                    break;
                }
                j--;
            }

            if (i >= j){
                break;
            }

            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            i++;
            j--;
        }
        return String.valueOf(s);
    }

    public static void reverseString(char[] s) {

        int length = s.length;
        int i = 0 ;
        int j = length-1;

        while (i < j){
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            i++;
            j--;
        }
    }

    private static void clearReferences(ReferenceQueue queue) {
        while (true) {
            Reference reference = queue.poll();
            if (reference == null) {
                break; // no references to clear
            }
            // cleanup(reference);
        }
    }

    public static void main(String[] args){

        // List<Integer> list = List.of(278, 576, 496, 727, 410, 124, 338, 149, 209, 702, 282, 718, 771, 575,436);
        // List<Integer> list = List.of(1,7,2,4);
        List<Integer> list = List.of(19,10,12,10,24,25,22);
        System.out.println(nonDivisibleSubset(4, list));

    }
}
