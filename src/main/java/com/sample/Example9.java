package com.sample;

import java.util.*;
import java.util.stream.Collectors;

public class Example9 {

    public static void main(String[] args){

        System.out.println(findGCD(new int[]{2,5,7,3, 10}));
    }



    public static int findGCD(int[] nums) {

        Arrays.sort(nums);
        int shortest = nums[0];
        int largest = nums[nums.length-1];

        int max = 1;

        if (largest%shortest == 0){
            return shortest;
        }

        for (int i = 2 ; i <= shortest/2; i++){
            if (shortest%i == 0 && largest%i == 0){
                max = i;
            }
        }
        return max;
    }

    public static String replaceDigits(String s) {

        int length = s.length();
        String finalString = "";
        char previous = 'a';
        for (int i = 0 ; i < length; i++){

            char current = s.charAt(i);
            if (current >= '0' && current <= '9'){
                Integer count  = current - '0';
                while (count-- > 0){
                    previous++;
                }
                finalString += previous;
            }else {
                finalString += current;
            }
            previous = current;
        }

        return finalString;
    }

    public static String truncateSentence(String s, int k) {

        String[] splits = s.split(" ");
        String finalString = "";
        int length = splits.length;

        for(int i = 0 ; i < k; i++){
            finalString += splits[i] + " ";
        }

        return finalString.substring(0, finalString.length()-1);

    }

    public static int countGoodSubstrings(String s) {

        int start = 0;
        int end = 3;
        int count = 0;
        int length = s.length()+1;

        String current = "";
        while (end < length){
            current = s.substring(start, end);
            if (current.charAt(0) == current.charAt(1)
                    || current.charAt(0) == current.charAt(2)
                    || current.charAt(1) == current.charAt(2)){
            }else {
                count++;
            }
            start++;
            end++;
        }


        return count;
    }

    public static String sortSentence(String s) {

        String[] splits = s.split(" ");
        int allStrings = splits.length;
        String finalString = "";

        Map<Integer, String> map = new HashMap<>();
        for (String string : splits){

            String value = "";
            String index = "";
            int length = string.length();
            for (int i = 0 ; i < length; i++){
                char c = string.charAt(i);
                if (c >= '0' && c <= '9'){
                    index = c + "";
                    while (++i < length){
                        index += string.charAt(i);
                    }
                    break;
                }else {
                    value += c;
                }
            }
            map.put(Integer.valueOf(index), value);
        }

        for (int i = 1 ; i <= allStrings; i++){
            finalString += map.get(i) + " ";
        }

        return finalString.substring(0, finalString.length()-1);

    }

    public static int totalMoney(int n) {

        int sum = 0;
        int i = 0;
        int monday = 0;
        int initial = 0;
        while (i < n){

            int index = 0;
            while (index < 7 && index < n - i){
                sum += ++initial;
                index++;
            }
            i = i +7;
            initial = ++monday;

        }

        return sum;

    }

    public static List<Integer> minSubsequence(int[] nums) {

        Arrays.sort(nums);
        int wholeSum = 0;
        int length = 0;
        for (int i : nums){
            wholeSum += i;
            length++;
        }

        int sum = 0;
        for (int i = length-1 ; i > -1; i--){
            sum += nums[i];
            if (sum > wholeSum - sum ){
                List<Integer> list = new ArrayList<>();
                int index = length-1;
                while (index >= i){
                    list.add(nums[index--]);
                }
                return list;
            }
        }
        return null;

    }

    public static List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {

        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        Set<Integer> set3 = new HashSet<>();

        Set<Integer> result = new HashSet<>();

        for (int i : nums1){
            set1.add(i);
        }
        for (int i : nums2){
            set2.add(i);
        }
        for (int i : nums3){
            set3.add(i);
        }

        for (Integer integer : set1){
            if (set2.contains(integer)){
                result.add(integer);
            }else if (set3.contains(integer)){
                result.add(integer);
            }
        }

        for (Integer integer : set2){
            if (set3.contains(integer)){
                result.add(integer);
            }
        }

        return result.stream().collect(Collectors.toList());

    }

    public int numOfStrings(String[] patterns, String word) {
        int count = 0;
        for (String string : patterns){
            if (word.contains(string)){
                count++;
            }
        }
        return count;
    }

    public static int maxProductDifference(int[] nums) {

        Arrays.sort(nums);
        int length = nums.length;
        return (nums[length-1] * nums[length-2]) - (nums[0] * nums[1]);

    }

    public static int minTimeToType(String word) {

        char prev = 'a';
        int spiralLength = 'z'-'a';

        int time = 0;

        int length = word.length();
        for (int i = 0 ; i < length; i++, time++){

            int diff = 0;
            char current = word.charAt(i);
            if (prev > current){
                diff = prev - current;
                if (diff > spiralLength/2){
                    time += 'z' - prev;
                    time += current - 'a';
                    time++;
                }else {
                    time += diff;
                }
            }else {

                diff = current - prev;
                if (diff > spiralLength/2){
                    time += prev - 'a';
                    time += 'z' - current;
                    time++;
                }else {
                    time += diff;
                }

            }
            prev = current;
        }
        return time;
    }

    public static boolean isSumEqual(String firstWord, String secondWord, String targetWord) {

        int firstValue = 0;
        int secondValue = 0;
        int targetValue = 0;

        int firstLength = firstWord.length();
        int secondLength = secondWord.length();
        int targetLength = targetWord.length();

        int length = firstLength;
        int index = 0;
        while (length > 0){
            firstValue += Math.pow(10, length-- - 1)*(firstWord.charAt(index++)-'a');
        }

        length = secondLength;
        index = 0;
        while (length > 0){
            secondValue += Math.pow(10, length-- - 1)*(secondWord.charAt(index++)-'a');
        }

        length = targetLength;
        index = 0;
        while (length > 0){
            targetValue += Math.pow(10, length-- - 1)*(targetWord.charAt(index++)-'a');
        }

        return firstValue+secondValue == targetValue;
    }

    public static boolean checkZeroOnes(String s) {

        int zeros = 0;
        int ones = 0;

        int zeroLongest = 0;
        int oneLongest = 0;
        char current = 'a';

        int length = s.length();
        for (int i = 0 ; i < length; i++){

            char previous = current;
            current = s.charAt(i);

            if (current == previous){
                if (current == '0'){
                    zeroLongest++;
                    if (zeroLongest > zeros){
                        zeros = zeroLongest;
                    }
                } else if (current == '1'){
                    oneLongest++;
                }
                if (oneLongest > ones){
                    ones = oneLongest;
                }
            }else {
                if (current == '0'){
                    zeroLongest = 1;
                } else if (current == '1'){
                    oneLongest = 1;
                }
            }
        }

        if (zeroLongest > zeros){
            zeros = zeroLongest;
        }
        if (oneLongest > ones){
        ones = oneLongest;
        }
        return ones > zeros;
    }

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
