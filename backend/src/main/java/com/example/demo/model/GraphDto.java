package com.example.demo.model;

import java.util.ArrayList;

public class GraphDto {
    public ArrayList<Vertex> vertices;

    public GraphDto() {
        vertices = new ArrayList<>();
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }
}
