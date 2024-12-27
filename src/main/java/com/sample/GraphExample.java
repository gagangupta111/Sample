package com.sample;

import java.util.*;

class NodeGraph{

    public int value;
    public List<NodeGraph> list;
}

public class GraphExample {

    public static void main(String[] args){

        System.out.println("Hello!");

        NodeGraph nodeGraph1 = new NodeGraph();
        nodeGraph1.value = 1;

        NodeGraph nodeGraph2 = new NodeGraph();
        nodeGraph2.value = 2;

        NodeGraph nodeGraph3 = new NodeGraph();
        nodeGraph3.value = 3;

        NodeGraph nodeGraph4 = new NodeGraph();
        nodeGraph4.value = 4;

        NodeGraph nodeGraph5 = new NodeGraph();
        nodeGraph5.value = 5;

        nodeGraph1.list = new ArrayList<>();
        nodeGraph1.list.add(nodeGraph2);
        nodeGraph1.list.add(nodeGraph3);

        nodeGraph2.list = new ArrayList<>();
        nodeGraph2.list.add(nodeGraph4);
        nodeGraph2.list.add(nodeGraph5);

        DFS(nodeGraph1);
    }

    public static List<NodeGraph> DFS(NodeGraph root){

        // stack make it DFS, Queue make it BFS
        Stack<NodeGraph> queue = new Stack<>();
        queue.add(root);

        LinkedHashSet<NodeGraph> visited = new LinkedHashSet<>();
        DFS(queue, visited);

        for (NodeGraph nodeGraph: visited){
            System.out.println(nodeGraph.value);
        }
        return null;
    }

    public static void DFS(Stack<NodeGraph> queue, LinkedHashSet<NodeGraph> visited){

        if (queue.isEmpty()){
            return;
        }

        NodeGraph current = queue.pop();
        while (!queue.isEmpty() && visited.contains(current)){
            current = queue.pop();
        }

        visited.add(current);
        if (current.list != null){
            queue.addAll(current.list);
        }
        DFS(queue, visited);
    }

}
