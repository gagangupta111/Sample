package com.sample.mergeIntervals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Main {

    static class ComparatorIntArrays implements java.util.Comparator<List<Integer>>{

        @Override
        public int compare(List<Integer> o1, List<Integer> o2) {
            return o1.get(0) - o2.get(0);
        }
    }

    public static int[][] merge(int[][] ints){

        List<List<Integer>> intervals = new ArrayList<>();
        for (int[] ints1 : ints){
            intervals.add(List.of(ints1[0], ints1[1]));
        }
        Collections.sort(intervals, new ComparatorIntArrays());

        List<List<Integer>> newIntervals = new ArrayList<>();
        int size = intervals.size();
        int originalSize = size;

        while (size > 1){
            List<Integer> firstInterval = intervals.get(0);
            List<Integer> current = intervals.get(1);
                // merge them together if next is in range of previous, remove previous, add merged one to new interval
                // if next is completely larger than simply remove first interval and add to new interval
                if (current.get(0) >= firstInterval.get(0) && current.get(0) <= firstInterval.get(1)){
                    if (current.get(1) <= firstInterval.get(1)){

                        intervals.remove(current);

                    }else {
                        List<Integer> newInterval = new ArrayList<>();
                        newInterval.add(firstInterval.get(0));
                        newInterval.add(current.get(1));

                        intervals.remove(firstInterval);
                        intervals.remove(current);

                        intervals.add(newInterval);

                    }
                }else if (current.get(0) > firstInterval.get(1)){
                    intervals.remove(firstInterval);
                    newIntervals.add(firstInterval);
                }
                Collections.sort(intervals, new ComparatorIntArrays());
                size = intervals.size();
        }

        if (size == 1){
            newIntervals.add(intervals.get(0));
        }

        int newSize = newIntervals.size();
        ints = new int[newSize][2];
        for (int i = 0 ; i < newSize; i++){
            ints[i][0] = newIntervals.get(i).get(0);
            ints[i][1] = newIntervals.get(i).get(1);
        }

        if (newSize == originalSize){
            return ints;
        }else {
            return merge(ints);
        }
    }

    public static void main(String[] args){

        int[][] ints = new int[][]{{1,7},{2,7},{8,10},{11,18}};
        ints = merge(ints);
        for (int[] list : ints){
            System.out.println(list[0] + " " + list[1]);
        }

    }

}
