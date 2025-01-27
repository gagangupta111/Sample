package com.hackerrank;

import java.util.*;
import java.util.stream.Collectors;

public class QueensAttack {

    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
        // Write your code here

        // calculate dots on all 8 sides, one by one
        // on each side check obstacles and recalculate dots
        // remove obstacles

        int total = 0;
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 1; i < 9; i++){

            list = returnDots(n, r_q, c_q, i);
            List<List<Integer>> filtered = new ArrayList<>();

            for (List<Integer> obs : obstacles){
                if (list.contains(obs)){
                    filtered.add(obs);
                }
            }

            obstacles.remove(filtered);

            for (List<Integer> obstacle : filtered){
                list.removeAll(returnDots(n, obstacle.get(0), obstacle.get(1), i));
                list.remove(obstacle);
            }
            total += list.size();
        }

        return total;
    }

    public static List<List<Integer>> returnDots(int n, int row, int column, int caseID){

        List<List<Integer>> list = new ArrayList<>();
        switch (caseID){

            case 1:
                while (--column > 0){
                    list.add(List.of(row, column));
                }
                break;
            case 2:
                while (++column < n+1){
                    list.add(List.of(row, column));
                }
                break;
            case 3:
                while (--row > 0){
                    list.add(List.of(row, column));
                }
                break;
            case 4:
                while (++row < n+1){
                    list.add(List.of(row, column));
                }
                break;
            case 5:
                while (--column > 0 && --row > 0){
                    list.add(List.of(row, column));
                }
                break;
            case 6:
                while (++column < n+1 && ++row < n+1){
                    list.add(List.of(row, column));
                }
                break;
            case 7:
                while (++column < n+1 && --row > 0){
                    list.add(List.of(row, column));
                }
                break;
            case 8:
                while (--column > 0 && ++row < n+1){
                    list.add(List.of(row, column));
                }
                break;
        }
        return list;
    }

    public static void main(String[] args) throws Exception{

        List<Integer> list = List.of(4,3);
        List<List<Integer>> obstacles = new ArrayList<>();
        obstacles.add(list);

        list = List.of(3,2);
        obstacles.add(list);

        System.out.println(queensAttack(5, 2, 2,3, obstacles));

    }
}
