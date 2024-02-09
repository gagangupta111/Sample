package com;

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

        int[] array = {0,0,0,1,0,1,0,1,0,1};
        System.out.println(array.length);
        sort(array);

        for (int i : array){
            System.out.println(i);
        }

        System.out.println(array.length);
    }
}
