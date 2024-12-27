package com.sample;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Sample101 {

    public static void main(String[] args){

        char x = new Character('c');
        System.out.println(x);

    }

    public int countSegments(String s) {

        return s.split(" ").length;

    }

    public boolean isIsomorphic(String s, String t) {

        // create map of previous mappings
        // every character will either be new of already part of map
        // if already part of map then value shall match with t string
        // else if not part of map then 2 cases
        // the t char has already been taken or not (check it with set of previous t chars )
        // if t char has already been taken then false else add it to map

        int length = s.length();
        int length1 = t.length();

        if (length != length1){
            return false;
        }

        Map<Character, Character> map = new HashMap<>();
        Map<Character, Character>  previousTChars = new HashMap<>();

        for (int i = 0 ; i < length; i++){

            Character sChar = s.charAt(i);
            Character tChar = t.charAt(i);
            Character mapChar = map.get(sChar);

            if (mapChar != null){
                if (tChar != mapChar){
                    return false;
                }
            }

            if (previousTChars.keySet().contains(tChar)){
                Character reverseTchar = previousTChars.get(tChar);
                if (!reverseTchar.equals(sChar)){
                    return false;
                }
            }

            previousTChars.put(tChar, sChar);
            map.put(sChar, tChar);
        }
        return true;
    }

    public static void streamAPIs(){

        Sample101 sample101 = new Sample101();
                // List of lists of names
                List<List<String>> listOfLists = Arrays.asList(
                        Arrays.asList("Reflection", "Collection", "Stream"),
                        Arrays.asList("Structure", "State", "Flow"),
                        Arrays.asList("Sorting", "Mapping", "Reduction", "Stream")
                );

                // Create a set to hold intermediate results
                Set<String> intermediateResults = new HashSet<>();

                // Stream pipeline demonstrating various intermediate operations
                List<String> result = listOfLists.stream()
                        .flatMap(List::stream)               // Flatten the list of lists into a single stream
                        .filter(s -> s.startsWith("S"))      // Filter elements starting with "S"
                        .map(String::toUpperCase)            // Transform each element to uppercase
                        .distinct()                          // Remove duplicate elements
                        .sorted()                            // Sort elements
                        .peek(s -> intermediateResults.add(s)) // Perform an action (add to set) on each element
                        .collect(Collectors.toList());       // Collect the final result into a list

                // Print the intermediate results
                System.out.println("Intermediate Results:");
                intermediateResults.forEach(System.out::println);

                // Print the final result
                System.out.println("Final Result:");
                result.forEach(System.out::println);


    }

    public List<String> summaryRanges(int[] nums) {

        List<String> list = new LinkedList<>();

        int length = nums.length;
        int start = 0;
        int current = 0;
        String valid = "";

        if (length == 0){
            return list;
        }

        for (int i = 0 ; i < length; i++){
            if (nums[i] == nums[current] || nums[i] == nums[current]+1){

            }else {

                if (start == current){
                    valid = "" + nums[start];
                }else {
                    valid = "" + nums[start] + "->" + nums[current];
                }
                start = i;
                list.add(valid);
            }
            current = i;
        }

        if (start == current){
            valid = "" + nums[start];
        }else {
            valid = "" + nums[start] + "->" + nums[current];
        }
        list.add(valid);

        return list;
    }

    public int singleNumber(int[] nums) {

        Set<Integer> set = new HashSet<>();
        for (int i : nums){
            if (set.contains(i)){
                set.remove(i);
            }else {
                set.add(i);
            }
        }

        for (int i : set){
            return i;
        }
        return -1;
    }

        public void mergeSort(int[] array, int i, int j){

        if (j-i > 1){
            mergeSort(array, i, (i+j)/2);
            mergeSort(array, (i+j)/2, j);
        }

    }

    public boolean isUpperAlphaBet(char c){
        if (c > 64 && c < 91){
            return true;
        }
        return false;
    }

    public boolean isLowerAlphaBet(char c){
        if (c > 96 && c < 123){
            return true;
        }
        return false;
    }

    public char lowerCase(char c){
        return (char) (97 + (c - 65));
    }

    public boolean isNumeric(char c){
        if (c > 47 && c < 58){
            return true;
        }
        return false;
    }

    public boolean isPalindrome(String s) {

        String finalString = "";
        int length = s.length();
        int finalLength = 0;
        for (int i = 0 ; i< length; i++){
            char c = s.charAt(i);
            if (isUpperAlphaBet(c)){
                finalString += lowerCase(c);
                finalLength++;
            } else if (isLowerAlphaBet(c) || isNumeric(c)) {
                finalString += (c);
                finalLength++;
            }
        }

        System.out.println(finalString);

        for (int i = 0, j = finalLength-1 ; i < j; i++, j--){

            if (finalString.charAt(i) != finalString.charAt(j)){
                return false;
            }
        }
        return true;
    }


    public static void testMultiThreadSingleton(){

        int n = 5000;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(n);

        try {
            for (int i = 0 ; i < n; i++){
                executor.execute(() ->
                {
                    ThreadSingleton singleton = new ThreadSingleton(start, done);
                    singleton.start();
                });
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            start.countDown();
            done.await(3, TimeUnit.SECONDS);
            System.out.println("All Finished!");

            System.out.println(ThreadSingleton.set.size());
            for (String string : ThreadSingleton.set){
                System.out.println(string);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (!executor.isTerminated()) {
                System.out.println(("cancelling all non-finished tasks"));
                executor.shutdownNow();
                System.out.println(("shutdown finished"));
            }
        }
    }

    public boolean isSameTree(TreeNode left, TreeNode right){

        if (left == null && null == right){
            return true;
        } else if (left == null || right == null){
            return false;
        }

        if (left.val != right.val){
            return false;
        }

        boolean l = isSameTree(left.left, right.left);
        boolean r = isSameTree(left.right, right.right);
        return l & r;

    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {

        int num1Index = 0;
        int i = 0;

        for (; i < n; i++){

            // find which one is smaller
            // if num1 is smaller then keep it else if num2 is smaller then add it in num1, before move all elements in num1 1 step ahead
            if (m == 0){
                break;
            }
            if (nums1[num1Index] > nums2[i]){
                moveAhead(nums1, num1Index, m);
                nums1[num1Index] = nums2[i];
            }else {
                i--;
                m--;
            }
            num1Index++;
        }

        if (m == 0 && i < n){
            while (i < n){
                nums1[num1Index] = nums2[i];
                num1Index++;
                i++;
            }
        }

    }

    public void moveAhead(int[] nums1, int index, int m) {

        int start = index + m;
        while (start > index){
            nums1[start] = nums1[start-1];
            start--;
        }
    }

    public static void testPrefix() {

        Sample101 sample101 = new Sample101();
        int result = sample101.prefix("+-+**(9)(2)(100)(200)8+43");
        System.out.println(result);

    }

    public int prefix(String s) {

        Set<String> operations = new HashSet<>();
        operations.add("*");
        operations.add("/");
        operations.add("+");
        operations.add("-");

        String leftB = "(";
        String rightB = ")";

        Stack<String> stack = new Stack<>();
        String number = "";
        int previous = -1;
        int current = -1;

        int length = s.length();
        for (int i = 0; i < length; i++) {
            String sub = s.substring(i, i + 1);
            if (operations.contains(sub)) {
                stack.add(sub);
                continue;
            }

            if (leftB.equals(sub)){
                sub = s.substring(++i, i + 1);
                while (!rightB.equals(sub)){
                    number += sub;
                    sub = s.substring(++i, i + 1);
                }
            }

            if (rightB.equals(sub)){
                current = Integer.parseInt(number);
                number = "";
            }else {
                current = Integer.parseInt(sub);
            }

            if (previous == -1){
                previous = current;
            }else {
                String stackValue = stack.pop();
                int result = -1;

                switch (stackValue){
                    case "*":
                        result = previous*current;
                        break;
                    case "/":
                        result = previous/current;
                        break;
                    case "+":
                        result = previous+current;
                        break;
                    case "-":
                        result = previous-current;
                        break;
                }
                previous = result;

                if ("*".equals(stackValue)){

                }
            }
        }
        return previous;
    }


    public ListNode deleteDuplicates(ListNode head) {

        ListNode start = head;
        ListNode current = start;

        while (current != null){
            if (current.val == start.val){
                start.next = current.next;
            }else {
                start = current;
            }
            current = current.next;
        }
        return head;
    }

    public int lengthOfLastWord(String s) {

        String last = lastWord(s);
        return 0;

    }


    public String lastWord(String s) {

        int length = s.length();
        String save = "";
        String last = "";
        for (int i = 0 ; i < length; i++){
            char currentChar = s.charAt(i);
            if (currentChar == ' '){
                save = last;
                last = "";
            }else {
                last += currentChar;
            }
        }
        return last.equals(" ") ? save : last;
    }

    public int searchInsert(int[] nums, int target) {

        int length = nums.length;
        int i = 0;
        for (; i < length; i++){
            if (nums[i] >= target){
                return i;
            }
        }
        return i;
    }

    public int strStr(String haystack, String needle) {

        int needleLength = needle.length();
        int haystackLength = haystack.length();

        for (int i = 0 ; i+needleLength <= haystackLength; i++){
            String sub = haystack.substring(i, i+needleLength);
            if (sub.equals(needle)){
                return i;
            }
        }
        return -1;
    }

    public int removeElement(int[] nums, int val) {

        Queue<Integer> stack = new LinkedList<>();
        int length = nums.length;
        int total = 0;

        for (int i = 0 ; i < length; i++){
            if (nums[i] == val){
                stack.add(i);
                total++;
            }else {
                if (!stack.isEmpty()){
                    nums[stack.remove()] = nums[i];
                    stack.add(i);
                }
            }
        }
        return length-total;
    }

    public boolean isValid(String s) {

        Map<String, String> map = new HashMap<>();
        map.put("(", ")");
        map.put("{", "}");
        map.put("[", "]");

        Map<String, String> reverseMap = new HashMap<>();
        reverseMap.put(")", "(");
        reverseMap.put("}", "{");
        reverseMap.put("]", "[");

        Set<String> openBset = map.keySet();

        Stack<String> stack = new Stack<>();
        int length = s.length();

        for (int i = 0 ; i < length; i++){
            String temp = s.substring(i,i+1);
            if (openBset.contains(temp)){
                stack.add(temp);
            }else {
                String open = reverseMap.get(temp);
                if (stack.empty() || !stack.peek().equals(open)){
                    return false;
                }else {
                    stack.pop();
                }
            }
        }
        return stack.empty();
    }

    public int removeDuplicates(int[] nums) {

        int length = nums.length;

        int current  = 0;
        int start = 0;

        while (current < length){

            while (current+1 < length && nums[current] == nums[current+1]){
                current = current+1;
            }

            if (current+1 == length){
                break;
            }

            if (nums[current+1] > nums[start]){
                nums[start+1] = nums[current+1];
                start = start+1;
                current++;
            }

        }
        return start+1;
    }

    public static String longestCommonPrefix(String[] strs) {

        String finalString = "";

        int length = strs.length;
        int[] lengths = new int[length];
        for (int i = 0; i < length; i++){
            lengths[i] = strs[i].length();
        }

        String first = strs[0];
        int firstLength = first.length();

        here:
        for (int i = 0; i < firstLength; i++){

            char c = first.charAt(i);

            for (int j = 1; j < length; j++){
                String string = strs[j];
                if (lengths[j] >= i+1 && c == string.charAt(i)){

                }else {
                    break here;
                }
            }
            finalString += c;
        }
        return finalString;
    }
}

class Car{

    // everythog insode Car is private
    // except the newCar builder method which returns Builder
    // all variables name setting happens via buyilder only
    private String name;

    private Car(){}

    private Car setName(String name){
        this.name = name;
        return this;
    }

    private static Car build(CarBuilder carBuilder){
        Car car = new Car();
        car.setName(carBuilder.getName());
        return car;
    }

    public static CarBuilder newCar(){
        return new CarBuilder();
    }

    public static class CarBuilder{
        private String name;

        public CarBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public String getName() {
            return name;
        }

        public Car build(){
            return Car.build(this);
        }
    }
}

class TreeNode{

    public int val;
    public TreeNode left;
    public TreeNode right;

}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Singleton{

    private static Singleton singleton;

    private Singleton(){}

    public static Singleton getInstance(){

        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }

        return singleton;
    }

}

class ThreadSingleton extends Thread{

    public static Set<String> set = new HashSet<>();
    private CountDownLatch start;
    private CountDownLatch done;

    public ThreadSingleton(CountDownLatch start, CountDownLatch done) {
        this.start = start;
        this.done = done;
    }

    @Override
    public void run() {

        try {
            start.await();
            Singleton singleton = Singleton.getInstance();
            set.add(singleton.toString());
            done.countDown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
