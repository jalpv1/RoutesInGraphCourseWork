package com.example.demo.mapper;

import com.example.demo.model.Edge;
import com.example.demo.model.Graph;
import com.example.demo.model.GraphDto;
import com.example.demo.model.Vertex;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class ModelsMapper {
    public static GraphDto mapper(Graph graph) {
        GraphDto graphDto = new GraphDto();
        graphDto.setVertices(graph.getVertices());
        for (int i = 0; i < graph.getEdges().size(); i++) {
            graphDto.getVertices().get(i).adjacencies = findEdges(graph, graphDto.getVertices().get(i));
        }
        return graphDto;

    }

    public static GraphDto mapper2(Graph graph) {
        GraphDto graphDto = new GraphDto();
        LinkedList<Vertex> frontier = new LinkedList<>();
        ArrayList<Vertex> explored = new ArrayList<>();
        frontier.add(graph.vertices.get(0));
        while (true) {
            if (frontier.isEmpty()) {
                return graphDto;
            }
            Vertex vertex = frontier.poll();
            explored.add(vertex);
            ArrayList<Edge> edges = findEdges(graph, vertex);
            for (Edge edge : edges) {
                vertex.adjacencies.add(edge);

                if (!explored.contains(edge.to)) {
                    edge.to.previous = vertex;
                    frontier.add(edge.to);
                }
                if (explored.contains(edge.to)) {
                //   System.out.println("expl");
                }
            }
            if(!vertex.adjacencies.isEmpty()) {
                graphDto.vertices.add(vertex);
            }
        }
    }

    private static ArrayList<Edge> findEdges(Graph graph, Vertex vertex) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (Edge e : graph.getEdges()) {
            if (e.from.name.equals(vertex.name)) {
                edges.add(e);
            }
        }
        return edges;

    }

    public static String pathCreate(List<Vertex> path) {
        String pathStr = "";
        for (Vertex v : path) {
            pathStr = pathStr + " -> " + v.name;
        }
        return pathStr;
    }

    public static boolean mycon(List<Vertex> arr, Vertex v) {
        for (Vertex ver :
                arr) {
            if (ver.name.equals(v.name)) {
                return true;
            }


        }
        return false;
    }
}
