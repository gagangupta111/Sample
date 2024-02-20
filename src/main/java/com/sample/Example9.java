package com.sample;

import java.util.*;

public class Example9 {

    public static int returnSides(int[][] grid, int x, int y, int rows, int columns) {

        int count = 0;
        if (x == 0 || grid[x-1][y] == 0){
            count++;
        }
        if (y == 0 || grid[x][y-1] == 0){
            count++;
        }
        if (x == rows-1 || grid[x+1][y] == 0){
            count++;
        }
        if (y == columns-1 || grid[x][y+1] == 0){
            count++;
        }
        return count;
    }

    public static int islandPerimeter(int[][] grid) {

        int rows = grid.length;
        int columns = grid[0].length;
        int peri = 0;

        for (int i = 0 ; i < rows; i++){
            for (int j = 0 ; j < columns; j++){
                if (grid[i][j] == 1){
                    peri += returnSides(grid, i, j, rows, columns);
                }
            }
        }
        return peri;
    }

    public static void main(String[] args){

        int[][] grid = {{0,1,0,0},{1,1,1,0},{0,1,0,0},{1,1,0,0}};
        System.out.println(islandPerimeter(grid));

    }

    public static int thirdMax(int[] nums) {

        Integer[] array = new Integer[3];
        array[0] = Integer.MIN_VALUE;
        array[1] = Integer.MIN_VALUE;
        array[2] = Integer.MIN_VALUE;

        for (int i : nums){
            if (i >= array[0]){
                array[2] = array[1];
                array[1] = array[0];
                array[0] = i;
            }else if (i >= array[1]){
                array[2] = array[1];
                array[1] = i;
            }else if (i >= array[2]){
                array[2] = i;
            }
        }
        if (array[2] > Integer.MIN_VALUE){
            return array[2];
        }else return array[0];
    }

    public static int isPrefixOfWord(String sentence, String searchWord) {

        int length = searchWord.length();
        String[] splits = sentence.split(" ");
        int size = splits.length;

        for(int i = 0 ; i < size; i++){

            String string = splits[i];
            int stringLength = string.length();
            if (stringLength >= length && string.substring(0, length).equals(searchWord)){
                return i+1;
            }
        }
        return -1;
    }

    public static int maxScore_find(String s, int startIndex, int endIndex, char c) {

        int count = 0;
        while (startIndex <= endIndex){
            if (s.charAt(startIndex++) == c){
                count++;
            }
        }
        return count;
    }

    public static int maxScore(String s) {

        int score = 0;
        int i = 1;
        int length = s.length();
        while (i <= length-1){
            int left = maxScore_find(s, 0, i-1, '0');
            int right = maxScore_find(s, i, length-1, '1');
            if (left + right > score){
                score = left + right;
            }
            i++;
        }

        return score;
    }

    public static String reformatNumber(String number) {

        String[] splits = number.split(" ");
        number = Arrays.stream(splits).reduce((a,b) -> a+b).get();

        splits = number.split("-");
        number = Arrays.stream(splits).reduce((a,b) -> a+b).get();

        int length = number.length();
        int tempLength = length;

        int rem = length%3;
        if (rem == 1){
            tempLength = length-4;
        }else if (rem == 2){
            tempLength = length-2;
        }

        String finalString  = "";
        int i = 0;
        for (;i+3 <= tempLength; i= i+3){
            finalString += number.substring(i, i+3) + "-";
        }

        if (length - tempLength == 4){
            finalString += number.substring(i, i+2) + "-";
            finalString += number.substring(i+2, length);
        }else if (length - tempLength == 2){
            finalString += number.substring(i, i+2);
        }else {
            finalString = finalString.substring(0, finalString.length()-1);
        }

        return finalString;
    }

    public static int numberOfMatches(int n) {

        int matches = 0;

        while (n > 1){
            matches += n/2;
            n = n/2 + n%2;
        }
        return matches;
    }
    public static boolean arrayStringsAreEqual(String[] word1, String[] word2) {

        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();

        for (String string : word1){
            stringBuilder1.append(string);
        }

        for (String string : word2){
            stringBuilder2.append(string);
        }

        return stringBuilder1.toString().equals(stringBuilder2.toString());

    }

    public static int numWaterBottles(int numBottles, int numExchange) {

        int drank = numBottles;
        int empty = numBottles;

        while (empty >= numExchange){
            drank += empty/numExchange;
            empty = empty/numExchange + (empty%numExchange);
        }

        return drank;
    }

    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {

        int length = startTime.length;
        int count = 0;

        for (int i = 0 ; i < length; i++){
            if (startTime[i] <= queryTime && queryTime <= endTime[i]){
                count++;
            }
        }
        return count;
    }

    public boolean canBeEqual(int[] target, int[] arr) {

        int length1 = arr.length;
        int length2 = target.length;

        if (length1 != length2){
            return false;
        }

        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();

        for (int i : arr){
            Integer value = map1.get(i);
            if (value == null){
                map1.put(i, 1);
            }else {
                map1.put(i, value+1);
            }
        }

        for (int i : target){
            Integer value = map2.get(i);
            if (value == null){
                map2.put(i, 1);
            }else {
                map2.put(i, value+1);
            }
        }

        if (map1.keySet().size() != map2.keySet().size()){
            return false;
        }

        for (int i : map1.keySet()){
            if (map1.get(i) != map2.get(i)){
                return false;
            }
        }

        return true;

    }

    public static String generateTheString(int n) {

        String value = "";
        if (n%2 == 0){
            value = "a";
            for (int i = 1; i < n; i++ ){
                value += "b";
            }
        }else {
            for (int i = 0 ; i < n; i++){
                value += "a";
            }
        }
        return value;
    }

    public static int[] replaceElements(int[] arr) {

        int length = arr.length;
        int largest = -1;

        int current = -1;

        for (int i = length-1; i > -1; i--){

            current = arr[i];
            arr[i] = largest;
            if (current > largest){
                largest = current;
            }
        }
        return arr;
    }

    public static String removeOuterParentheses(String s) {

        List<String> list = new ArrayList<>();
        int length = s.length();
        Stack<Character> stack = new Stack<>();
        int lastIndex = 0;

        for (int i = 0; i < length; i++){
            Character c = s.charAt(i);
            if (c == '('){
                stack.add(c);
            }else {
                stack.pop();
            }
            if (stack.isEmpty()){
                list.add(s.substring(lastIndex,i+1));
                lastIndex = i+1;
            }
        }

        List<String> newList = new ArrayList<>();
        for (String string : list){
            newList.add(string.substring(1, string.length()-1));
        }

        return newList.stream().reduce((a,b) -> a+b).get();

    }

    public static int dominantIndex(int[] nums) {

        int largest = -1;
        int secondLargest = -1;
        int largestIndex = -1;
        int length = nums.length;

        for (int i = 0 ;i < length; i++){
            if (nums[i] >= largest){
                largest = nums[i];
                largestIndex = i;
            }else if (nums[i] >= secondLargest){
                secondLargest = nums[i];
            }
        }
        for (int i = 0 ;i < length; i++){
            if (nums[i] >= largest){
                largest = nums[i];
                largestIndex = i;
            }else if (nums[i] >= secondLargest){
                secondLargest = nums[i];
            }
        }
        if (largest >= secondLargest*2){
            return largestIndex;
        }else {
            return -1;
        }
    }
}
