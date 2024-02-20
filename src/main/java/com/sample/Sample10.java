package com.sample;

import java.io.FileInputStream;
import java.util.*;

public class Sample10 {

    public static String getResponseString(String key , List<Double> list) {
        return "key : " + key + " Min : " + list.get(0) + " Max: " + list.get(1) + " Latest : " + list.get(2);
    }


    public static Double getDoubleValue(String str) {
        try {
            Double value = Double.parseDouble(str);
            return value;
        } catch(Exception e){
            return null;
        }
    }

    public static void loadFile(String fileNameFullAddress) throws Exception {

        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(fileNameFullAddress);
            sc = new Scanner(inputStream, "UTF-8");

            String key = null;
            List<Double> list = new ArrayList<>();

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                Double value = getDoubleValue(line);

                if (value != null){
                    list.add(value);
                }else {
                    if (key != null){
                        List<Double> results = findMinMaxLatest(list);
                        System.out.println(getResponseString(key, results));
                        list = new ArrayList<>();
                    }
                    key = line;
                }
            }

            if (!list.isEmpty()){
                List<Double> results = findMinMaxLatest(list);
                System.out.println(getResponseString(key, results));
            }

            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (Exception exception){
            System.out.println(exception);
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

    }

    public static List<Double> findMinMaxLatest(List<Double> list){

        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;

        for (Double value : list){
            if (value > max){
                max = value;
            }
            if (value < min){
                min = value;
            }
        }
        List<Double> values = new ArrayList<>();
        values.add(min);
        values.add(max);
        values.add(list.get(list.size()-1));
        return values;
    }

    public static void main(String[] args) throws Exception{

        loadFile("C:\\Users\\gagan\\Downloads\\gitRepos\\Sample\\src\\main\\resources\\CUSIP_task_input 5.txt");

    }
}
