package com.streams;

import java.util.*;
import java.util.stream.Collectors;

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

    private final String name;
    private final Integer id;
    private final int size;

    public Data(String name, Integer id, int size) {
        this.name = name;
        this.id = id;
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
        dataList.add(new Data("1", 11, 11));
        dataList.add(new Data("2", 2, 2));
        dataList.add(new Data("3", 3, 3));

        Map<String, List<Data>> mapByNameAsKey = dataList.stream().collect(Collectors.groupingBy(Data::getName));
        for (String string : mapByNameAsKey.keySet()){
            System.out.println("key : " + string + " list : ");
            mapByNameAsKey.get(string).stream().forEach((a) -> System.out.println(a.getId()));
        }
        System.out.println("++++++++++++++++++");

        Map<String, Set<Data>> mapToSet = dataList.stream().collect(Collectors.groupingBy(Data::getName, Collectors.toSet()));
        for (String string : mapToSet.keySet()){
            System.out.println("key : " + string + " set : ");
            mapToSet.get(string).stream().forEach((a) -> System.out.println(a.getId()));
        }
        System.out.println("++++++++++++++++++");

        Map<String, Long> mapToCount = dataList.stream().collect(Collectors.groupingBy(Data::getName, Collectors.counting()));
        for (String string : mapToCount.keySet()){
            System.out.println("key : " + string + " count : " + mapToCount.get(string));
        }
        System.out.println("++++++++++++++++++");

        Map<String, List<Integer>> mapToCustomList = dataList
                .stream()
                .collect(Collectors
                        .groupingBy(Data::getName,
                                Collectors.mapping(Data::getId, Collectors.toList())));
        for (String string : mapToCustomList.keySet()){
            System.out.println("key : " + string + " custom : ");
            System.out.println(mapToCustomList.get(string).stream().map(a -> String.valueOf(a)).reduce((a,b) -> a + "_" + b).get());
        }
        System.out.println("++++++++++++++++++");

        Map<Pair, List<Data>> mapByPair = dataList.stream().collect(Collectors.groupingBy(data -> new Pair(data.getName(), data.getId())));
        for (Pair pair : mapByPair.keySet()){
            System.out.println("key : " + pair.getName() + " : " + pair.getId() + " pair value : " + mapByPair.get(pair));
            mapByPair.get(pair).stream().forEach((a) -> System.out.println(a.getId()));
        }
        System.out.println("++++++++++++++++++");

        Map<Data, List<Data>> mapByNewData =
                dataList.stream().collect(Collectors.groupingBy(data -> new Data(data.getName(), data.getId(), data.getSize())));
        for (Data data : mapByNewData.keySet()){
            System.out.println("key : " + data.getName() + " : " + data.getId() + " value : " + mapByNewData.get(data));
            mapByNewData.get(data).stream().forEach((a) -> System.out.println(a.getId()));
        }
        System.out.println("++++++++++++++++++");


    }


}
