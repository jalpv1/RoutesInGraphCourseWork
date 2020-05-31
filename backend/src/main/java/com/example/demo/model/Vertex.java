package com.example.demo.model;

import java.util.ArrayList;
import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    public  String name;
    public ArrayList<Edge> adjacencies;
    public double maxValuePath = -Double.POSITIVE_INFINITY;
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

    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                ", adjacencies=" + adjacencies +
                ", maxValuePath=" + maxValuePath +
                ", previous=" + previous +
                ", value=" + value +
                ", time=" + time +
                ", cost=" + cost +
                '}';
    }

    public int compareTo(Vertex other) {
        return Double.compare(maxValuePath, other.maxValuePath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return
                value == vertex.value &&
                time == vertex.time &&
                cost == vertex.cost &&
                Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, time, cost);
    }
}
