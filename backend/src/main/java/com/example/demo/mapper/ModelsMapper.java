package com.example.demo.mapper;

import com.example.demo.model.Edge;
import com.example.demo.model.Graph;
import com.example.demo.model.GraphDto;
import com.example.demo.model.Vertex;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
@Service
public class ModelsMapper {
    public static GraphDto mapper(Graph graph){
        GraphDto graphDto = new GraphDto();
        graphDto.setVertices(graph.getVertices());
       for(int i = 0; i< graph.getEdges().size();i++){
           graphDto.getVertices().get(i).adjacencies = findEdges(graph,graphDto.getVertices().get(i));
       }
        return graphDto;

    }
    private static ArrayList<Edge>findEdges(Graph graph, Vertex vertex){
        ArrayList<Edge> edges= new ArrayList<>();
        for (Edge e:graph.getEdges()) {
            if(e.from.name.equals(vertex.name)){
                edges.add(e);
            }
        }
        return edges;

    }
}
