package com.example.demo.services;

import com.example.demo.model.Edge;
import com.example.demo.model.Vertex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LimitsService {
    public static boolean checkLimits(List<Vertex> path, int costLimit, int timeLimit) {
        return checkTime(path, costLimit) && checkCost(path, timeLimit);
    }

    private static Edge getEdge(List<Edge> edges, Vertex vertex) {
     //   Edge e = edges.stream().filter(edge -> edge.to.equals(vertex)).findFirst().orElse(null);
        for (Edge ed:edges
             ) {
            if(ed.to.name.equals(vertex.name)){
                return ed;
            }
        }

        return null;
    }
   /* private static Edge getEdge(List<Edge> edges, Vertex vertex) {
        return  edges.stream().filter(edge -> edge.target.equals(vertex)).findFirst().orElse(null);
    }

    */


    private static boolean checkTime(List<Vertex> path, int timeLimit) {
        //int sum = path.stream().mapToInt(e -> e.time).sum();
        int sum = 0;
        //Collections.reverse(path);
        for (int i =0; i < path.size();i++) {
            if (i + 1 != path.size()) {
                Vertex v = path.get(i);
                Vertex v2 = path.get(i
                        +1);

                sum = sum+path.get(i).time + getEdge(path.get(i).adjacencies, path.get(i+1)).time;
            }
            else {
                sum = sum+path.get(i).time;
            }
        }
        return sum < timeLimit;
    }

    private static boolean checkCost(List<Vertex> path, int costLimit) {
        int sum = 0;
        //Collections.reverse(path);

            for (int i = 0; i < path.size(); i++) {
                if (i + 1 != path.size()) {
                    sum = sum + path.get(i).cost + getEdge(path.get(i).adjacencies, path.get(i + 1)).cost;
                } else {
                    sum = sum + path.get(i).cost;
                }
            }
        return sum < costLimit;
    }
}
