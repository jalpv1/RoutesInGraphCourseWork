package com.example.demo.model;

import java.util.ArrayList;

public class Graph {
    public  ArrayList<Vertex> vertices;
    public  ArrayList<Edge> edges;
    private int costLimit;
    private int timeLimit;

    public int getCostLimit() {
        return costLimit;
    }

    public void setCostLimit(int costLimit) {
        this.costLimit = costLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Graph() {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
    }

    public  ArrayList<Vertex> getVertices() {
        return vertices;
    }



    public  ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}
