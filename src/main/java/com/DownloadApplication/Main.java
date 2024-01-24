package com.DownloadApplication;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

interface Rule{

    public String rule(int value);

}

interface RuleNew extends Rule{

}

class Child extends RuleClass implements RuleNew, Rule {

    @Override
    public String rule(int value) {
        return null;
    }
}

class RuleClass{

    public RuleClass() {
    }

    public String varName;

    public Rule rule;

    public RuleClass(String varName, Rule rule) {
        this.varName = varName;
        this.rule = rule;
    }
}

public class Main {

    private static Map<String, List<RuleClass>> map = new HashMap<>();

    public static void createRules(){

        List<RuleClass> list = new ArrayList<>();
        RuleClass ruleClass = new RuleClass("a", (a) -> {
            if (a > 100){
                return "foo1";
            }else return null;
        });
        list.add(ruleClass);

        ruleClass = new RuleClass("a", (a) -> {
            if (a > 500){
                return "foo2";
            }else return null;
        });
        list.add(ruleClass);

        ruleClass = new RuleClass("b", (a) -> {
            if (a > 100){
                return "foo3";
            }else return null;
        });
        list.add(ruleClass);

        for (RuleClass ruleClass1: list){
            if (map.get(ruleClass1.varName) == null){
                map.put(ruleClass1.varName, new ArrayList<>());
            }
            map.get(ruleClass1.varName).add(ruleClass1);
        }


    }

    public static void main(String[] args) {

        Map<String, String> map1 = new HashMap() {
        };
        map1.put(null, null);

        for (String string : map1.keySet()){
            System.out.println("++++++++++");
            System.out.println(string);
            System.out.println(map1.get(string));
        }

        assert map1.size() == 5;

    }

    public static String applyRule(String ruleName, int value){

        List<RuleClass> list = map.get(ruleName);
        int count = 0;
        String returnValue = null;

        for (RuleClass rule : list){
            String tempReturnValue = rule.rule.rule(value);
            if (tempReturnValue != null){
                returnValue = tempReturnValue;
                count++;
                if (count > 1){
                    return null;
                }
            }
        }

        return returnValue;
    }

}

