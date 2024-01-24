package com.leetcode;

import java.util.ArrayList;
import java.util.List;

class Solution {

    public static void main(String[] args){
        Exception1();
    }

    public static void Exception1(){

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        for (Integer integer : list){
            list.removeIf((i) ->  i == 0);
        }

        for (int i = 0 ; i < list.size(); i++){
            list.remove(i);
        }

    }

    public static int lengthOfLastWord(String s) {

        String[] splits = s.split(" ");
        return splits[splits.length-1].length();

    }

    public static int searchInsert(int[] nums, int target) {

        int length = nums.length;
        for (int i = 0 ; i < length; i++){
            if (nums[i] >= target){
                return i;
            }
        }
        return length;
    }
}
