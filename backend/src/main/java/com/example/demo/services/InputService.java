package com.example.demo.services;


import com.example.demo.mapper.ModelsMapper;
import com.example.demo.model.Edge;
import com.example.demo.model.Graph;
import com.example.demo.model.GraphDto;
import com.example.demo.model.Vertex;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service

public class InputService {
    public static GraphDto  generateService(int sizeVertexes, int sizeEdges) {
        HashSet<String> names = namesGenerate2(sizeVertexes);
        Graph graph = new Graph();
        ArrayList<Vertex> vertices = (ArrayList<Vertex>) generateVertexes(sizeVertexes, names);
        graph.setVertices(vertices);
       // graph.setEdges((ArrayList<Edge>) generateEdges(sizeEdges, vertices));
        while (true) {
            ArrayList<Edge> edges = (ArrayList<Edge>) generateEdges(sizeEdges, vertices);
            if(!edges.isEmpty()){
                graph.setEdges(edges);
                break;
            }
        }
        return ModelsMapper.mapper2(graph);

    }

    /*  private ArrayList<Edge> addEdges(ArrayList<String> names){

      }

     */

    private static List<Vertex> generateVertexes(int size, HashSet<String>  names) {
        List<String> list = new ArrayList<String>(names);

        Random rnd = new Random();
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Vertex vertex =
                    new Vertex(list.get(i), rnd.nextInt(26), rnd.nextInt(26), rnd.nextInt(26));
           vertices.add(vertex);
            //   vertex.adjacencies = new ArrayList<>();
            //   vertex.adjacencies.add(new Edge(new Vertex(),rnd.nextInt(26),rnd.nextInt(26)));
        }
        return vertices;
    }

    private static List<Edge> generateEdges(int size, ArrayList<Vertex> vertices) {
        Random rnd = new Random();
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            while (true) {
                Vertex from = vertices.get((int) (Math.random() * vertices.size()-1));
                Vertex to = vertices.get((int) (Math.random() * vertices.size()-1));
                if (!from.equals(to) && !from.name.equals(to.name)) {
                    Edge edge = new Edge(from,
                            rnd.nextInt(26), rnd.nextInt(26),
                            to);
                    edges.add(edge);
                    break;
                }
                if(from.equals(to)){
                    System.out.println("g");
                }
            }

        }

        return edges;
    }

    private  static ArrayList<String> namesGenerate(int size) {
        ArrayList<String> names = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {

            while (true){
                String name =getChar();
                boolean check =checkUnique(names,name);
                if(!check) {
                    names.add(getChar());
                    break;
                }
            }
        }
        return names;
    }
    private  static HashSet<String> namesGenerate2(int size) {
        HashSet<String>  names = new HashSet<> (size);

        while (names.size() != size) {
// String name =getChar();
            // boolean check =checkUnique(names,name);
            //if(!check) {
            names.add(getChar() + getChar());
            //break;
            //  }

        }
        return names;
    }
     private static boolean checkUnique(List<String> names, String str){
         for (String n:names
              ) {
             if(n.equals(str)) {
                 return true;
             }
         }
        return false;
     }
    private static String getChar() {
        Random rnd = new Random();
        final String s = Character.toString(rnd.nextInt(26) + 'a');
        return s;

    }
}
