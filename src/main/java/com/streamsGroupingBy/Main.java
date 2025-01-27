package com.streamsGroupingBy;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// below code demonstrates various ways to use groupingBy in streams

class Pair{

    public String name;
    public Integer id;

    public Pair(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
class Data{

    private String name;
    private Integer id;
    private int size;

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", size=" + size +
                '}';
    }

    public Data() {
        id = 0;
        size = 0;
    }

    public Data(String name, Integer id, int size) {
        this.name = name;
        this.id = id;
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public Data merge(Data other) {
        this.size += other.getSize();
        this.id += other.getId();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return size == data.size && Objects.equals(name, data.name) && Objects.equals(id, data.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, size);
    }
}

public class Main {

    public static void main(String[] args){

        List<Data> dataList = new ArrayList<>();
        dataList.add(new Data("1", 1, 1));
        dataList.add(new Data("1", 11, 11));
        dataList.add(new Data("1", 12, 12));
        dataList.add(new Data("2", 2, 2));
        dataList.add(new Data("3", 3, 3));

        // reduction operator depends upon the result to be of same kind
        System.out.println("++++++++ list reduce to max ++++++++++");
        List<Integer> list = new ArrayList<>();
        System.out.println("max  : " + list.stream().reduce((data, data2) -> data2 > data ? data2 : data));

        System.out.println("++++++++map filtering++++++++++");
        // filtering somehow not working, need to check
        Map<String, Long> mapFiltering = dataList.stream().collect(Collectors.groupingBy(
                a -> a.getName(),
                Collectors.counting()));
        for (String string : mapFiltering.keySet()){
            System.out.println("key : " + string + " count : " + mapFiltering.get(string));
        }

        System.out.println("++++++++counting++++++++++");
        Map<String, Long> mapToCount = dataList.stream().collect(Collectors.groupingBy(Data::getName, Collectors.counting()));
        for (String string : mapToCount.keySet()){
            System.out.println("key : " + string + " count : " + mapToCount.get(string));
        }

        System.out.println("++++++++mapToMax++++++++++");
        Map<String, Optional<Data>> mapToMax = dataList
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Data::getName,
                                Collectors.reducing(
                                        BinaryOperator.maxBy(
                                                Comparator.comparing(Data::getSize)
                                        )
                                )));
        for (String string : mapToMax.keySet()){
            System.out.println("key : " + string + " mapToMax : " + mapToMax.get(string).orElseGet(null));
        }

        System.out.println("+++++++++mapToSum+++++++++");
        Map<String, Data> mapToSum = dataList.stream().collect(Collectors.groupingBy(
                Data::getName,
                Collector.of(
                        Data::new,
                        Data::merge,
                        Data::merge
                        )
        ));
        for (String string : mapToSum.keySet()){
            System.out.println("key : " + string + " mapToSum : " + mapToSum.get(string));
        }

        System.out.println("++++++++mapToCustomList++++++++++");
        Map<String, List<Integer>> mapToCustomList = dataList
                .stream()
                .collect(Collectors
                        .groupingBy(Data::getName,
                                Collectors.mapping(Data::getId, Collectors.toList())));
        for (String string : mapToCustomList.keySet()){
            System.out.println("key : " + string + " custom : ");
            System.out.println(mapToCustomList.get(string).stream().map(a -> String.valueOf(a)).reduce((a,b) -> a + "_" + b).get());
        }

        System.out.println("++++++++mapByPair++++++++++");
        Map<Pair, List<Data>> mapByPair = dataList.stream().collect(Collectors.groupingBy(data -> new Pair(data.getName(), data.getId())));
        for (Pair pair : mapByPair.keySet()){
            System.out.println("key : " + pair.getName() + " : " + pair.getId() + " pair value : " + mapByPair.get(pair));
            mapByPair.get(pair).stream().forEach((a) -> System.out.println(a.getId()));
        }

        System.out.println("++++++++ mapByNewData as key ++++++++++");
        Map<Data, List<Data>> mapByNewData =
                dataList.stream().collect(Collectors.groupingBy(data -> new Data(data.getName(), data.getId(), data.getSize())));
        for (Data data : mapByNewData.keySet()){
            System.out.println("key : " + data.getName() + " : " + data.getId() + " value : " + mapByNewData.get(data));
            mapByNewData.get(data).stream().forEach((a) -> System.out.println(a.getId()));
        }

    }


}
