package com.sample;

import java.io.FileInputStream;
import java.util.*;

// AB -> A1 -> A2
// AB -> B1 -> B2
interface ABInterface{}
class AB implements ABInterface{}
class A1 extends AB{}
class A2 extends A1{}

class B1 extends AB{}
class B2 extends B1{}


class Generic<T>{

    T value;

    public Generic(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}

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

    public static void generics(){
        List<Generic> list = new ArrayList<>();
        Generic<?> generic = new Generic<>(10);
        Generic<String> generic1 = new Generic<>("10");
        Generic<? extends Number> generic2 = new Generic<>(new Double(1));
        Generic<? super Integer> generic3 = new Generic<>(new Double(1));

        list.add(generic);
        list.add(generic1);
        int i = (int) list.get(0).getValue();
        String string = (String) list.get(1).getValue();

        System.out.println(i + string);
    }

    public static void genericsWildCards(){

        // this list can be : either A or extension of A
        // this list guaratee that the reading will be either A or anything extension of A
        // hence writing is not guarateed, i might not add anything, because if i add A, it might of type A1
        // most lowest child B2 can also not be added, because list might be A2
        // producers lists

        List<? extends AB> list1 = new ArrayList<AB>();
        List<? extends AB> list2 = new ArrayList<A1>();
        List<? extends AB> list3 = new ArrayList<B2>();

        // list1.add(new B2()); compilation error
        // list1.add(new AB()); compilation error

        for (AB ab : list1){
            System.out.println(ab);
        }
        for (AB ab : list2){
            System.out.println(ab);
        }
        for (AB ab : list3){
            System.out.println(ab);
        }

        // this list can be : either B1 or super class of B1
        // addition is guaranteed, any thing B1 or extension of B1, because parent can take child
        // but reading is not guarantee, because we dont know till where super classes are going,
        // only reading guarantee is Object, becase there is nothing super of Object

        List<? super B1> list4 = new ArrayList<B1>();
        List<? super B1> list5 = new ArrayList<AB>();
        List<? super B1> list6 = new ArrayList<Object>();

        list4.add(new B1());
        list4.add(new B2());
        // list4.add(new AB()); compilation error
        list5.add(new B2());
        list6.add(new B1());

        for (Object b1 : list4){
            System.out.println(b1);
        }

        // ? unknown type, means this list can be of any type,
        // hence we cant add anything
        // we can only read in form of objects

        List<?> list7 = new ArrayList<Integer>();
        List<?> list8 = new ArrayList<AB>();
        List<?> list9 = new ArrayList<Object>();

        // list7.add(new Integer(1)); cant add anything

    }

    public static void main(String[] args) throws Exception{




    }
}
