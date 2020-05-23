package com.example.demo.model;

import java.util.ArrayList;

public class Graph {
    public  ArrayList<Vertex> vertices;
    public  ArrayList<Edge> edges;

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
