
package com.sample;

public class Example8 {

    public static void shift(char[] array, int currIndex){

        if (currIndex == 0){
            return;
        }
        int temp = currIndex;
        while (temp != 0 && !isAlphabet(array, --temp)){

        }
        while (currIndex > temp+1 && currIndex != 0){
            char tempChar = array[currIndex];
            array[currIndex] = array[currIndex-1];
            array[currIndex-1] = tempChar;
            currIndex--;
        }
        if (!isAlphabet(array, temp)){
            char tempChar = array[currIndex];
            array[currIndex] = array[currIndex-1];
            array[currIndex-1] = tempChar;
        }
    }

    public static boolean isAlphabet(char[] array, int i){

        if (!(array[i] >= '0' && array[i] <= '9')){
            return true;
        }
        return false;

    }
    public static String sortString(String string){

        // 1jgh2hg3jh
        // find the alphabet, shift it till previous alphabet

        int length = string.length();
        char[] array = string.toCharArray();

        for (int i = 0 ; i < length; i++){

            if (isAlphabet(array, i)){
                shift(array, i);
            }

        }

        return String.valueOf(array);
    }

    public static void main(String[] args){

        System.out.println("Hello");
        System.out.println(sortString("789abc123def"));

    }

}
