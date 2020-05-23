package com.example.demo.model;

import org.springframework.stereotype.Component;

public class Edge {
    public  Vertex from;
    //public final double weight;
    public int time;
    public int cost;
    public  Vertex to;

    public Edge(Vertex from, int time, int cost, Vertex to) {
       this.from = from;
        this.time = time;
        this.cost = cost;
        this.to = to;
    }
}
