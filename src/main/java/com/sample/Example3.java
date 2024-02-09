
package com.sample;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Example3 {


    public static void adjust(int[] nums, int prevIndex, int currentIndex) {

        nums[prevIndex] = nums[currentIndex];

    }
    public static int removeDuplicates(int[] nums) {

        int length = nums.length;
        int previous = nums[0];
        int prevIndex = 0;
        for (int i = 1 ; i < length; i++){
            if (nums[i] == previous){
                continue;
            }
            if ( i - prevIndex > 1 ){
                prevIndex++;
                adjust(nums, prevIndex, i);
                prevIndex = prevIndex+1;
                previous = nums[i];
            }else {
                previous = nums[i];
                prevIndex = i;
            }
        }

        int total = length - prevIndex;
        while (prevIndex < length){
            nums[prevIndex++] = 0;
        }
        return total;

    }

    public static void main(String[] args){

        int[] array = {1,1,2,3,4,4};
        int total = removeDuplicates(array);
        for (int i : array){
            System.out.println(i);
        }
        System.out.println(total);

        OptionalInt xyz = Arrays.stream(array).reduce((a, b) -> a+b);
        System.out.println("optional int  : " + xyz.getAsInt());

        List<String> strings = new ArrayList<>();
        strings.add("Hello");
        strings.add("Ram");
        strings.add("Hello");
        strings.add("Sam");
        strings.add("Hello");
        strings.add("Yam");
        strings.add("Hello");
        strings.add("Raj");
        strings.add("Hello");
        strings.add("Raj");
        Map<String, Long> countMap = strings.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    }

}
