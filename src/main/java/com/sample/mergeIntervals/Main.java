package com.sample.mergeIntervals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class Main {

    public static ListNode mergeNodes(ListNode head) {

        ListNode previous = null;
        ListNode first = null;
        ListNode current = null;
        ListNode finalCurrentNode = null;
        ListNode finalHeadNode = null;

        int sum = 0;
        boolean flag = true;

        previous = head;
        first = head.next;
        current = first;

        while (flag){
            while (current != null && current.val != 0){
                sum += current.val;
                current = current.next;
            }
            if (current == null){
                break;
            }else {
                first = new ListNode(sum);
                if (finalHeadNode == null){
                    finalHeadNode = first;
                    finalCurrentNode = finalHeadNode;
                }else {
                    finalCurrentNode.next = first;
                }
                sum = 0;
                previous.next = first;

                previous = current;
                first = current.next;
                current = current.next;
            }
        }
        return finalHeadNode;
    }

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

        ListNode head = new ListNode(0);
        head.next = new ListNode(5);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(9);
        head.next.next.next.next = new ListNode(18);
        head.next.next.next.next.next = new ListNode(0);

        head = mergeNodes(head);
        while (head != null){
            System.out.println(head.val);
            head = head.next;
        }
    }

}
