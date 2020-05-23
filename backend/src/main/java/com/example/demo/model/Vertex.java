package com.example.demo.model;

import java.util.ArrayList;
import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    public  String name;
    public ArrayList<Edge> adjacencies;
    public double maxDistance = -Double.POSITIVE_INFINITY;
    public Vertex previous;
    public int value;
    public int time;
    public int cost;


    public Vertex(String argName, int value,int time,int cost) {
        name = argName;
        adjacencies = new ArrayList<>();
        this.value = value;
        this.time = time;
        this.cost = cost;
    }
    public Vertex() {

    }
    public String toString() {
        return name;
    }

    public int compareTo(Vertex other) {
        return Double.compare(maxDistance, other.maxDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.maxDistance, maxDistance) == 0 &&
                Objects.equals(name, vertex.name) &&
                Objects.equals(adjacencies, vertex.adjacencies) &&
                Objects.equals(previous, vertex.previous);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, adjacencies, maxDistance, previous);
    }
}
