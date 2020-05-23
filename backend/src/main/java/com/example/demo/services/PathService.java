package com.example.demo.services;
import com.example.demo.model.Edge;
import com.example.demo.model.GraphDto;
import com.example.demo.model.Vertex;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathService {
    private ArrayList<Vertex> computePaths(Vertex source) {
        source.maxDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        ArrayList<Vertex> explored = new ArrayList<>();
        vertexQueue.add(source);
        explored.add(source);

        while (!vertexQueue.isEmpty()) {

            Vertex u = vertexQueue.poll();
            ArrayList<Edge> neigh = u.adjacencies;
            for (Edge e : neigh) {
                Vertex v = e.to;
                double distanceThroughU = u.maxDistance + u.value;
                if (distanceThroughU > v.maxDistance) {
                    vertexQueue.remove(v);

                    v.maxDistance = distanceThroughU;
                    if (u.previous != null && !u.previous.equals(v)) {
                        v.previous = u;

                    }
                    if (u.previous != null && !u.previous.equals(v)) {
                        System.out.println("h");
                    }
                    if (!explored.contains(v)) {

                        explored.add(v);
                        vertexQueue.add(v);

                    }
                }
            }
        }

        source.previous = null;
        source.maxDistance = 0.;
  return explored;
    }

    private List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {

            path.add(vertex);

        }

        Collections.reverse(path);
        return path;
    }

    public List<Vertex> findPathServiceDejkstra(GraphDto graph, int costLimit, int timeLimit) {
        List<List<Vertex>> paths = new ArrayList<>();
        for (Vertex from :
                graph.getVertices()) {
            graph.setVertices( computePaths(from));
            System.out.println("From " + from.name);
            for (Vertex to :
                    graph.getVertices()) {
                if (!from.equals(to)) {
                    List<Vertex> currentPath = getShortestPathTo(to);

                   if (LimitsService.checkLimits(currentPath, costLimit, timeLimit)) {
                        paths.add(currentPath);
                   }
                }
            }


        }

        return findPathWithMaxCost(paths);

    }
    public List<Vertex> findPathServiceGreedy(GraphDto graph, int costLimit, int timeLimit) {
        List<List<Vertex>> paths = new ArrayList<>();

        for (Vertex from :
                graph.getVertices()) {
            paths.add(greedy(from,costLimit,timeLimit));
        }
        return findPathWithMaxCost(paths);
    }


        private List<Vertex> findPathWithMaxCost(List<List<Vertex>> paths) {
        int maxCost = 0;
        List<Vertex> maxPath = new ArrayList<>();
        for (List<Vertex> path : paths) {
            int cost = calculateValue(path);
            if (cost > maxCost) {
                maxPath = path;
            }
        }
        return maxPath;
    }

    private int calculateValue(List<Vertex> path) {
        int val = 0;
        for (Vertex v : path) {
            val = val + v.cost;
        }
        return val;
    }
    public  List<Vertex> greedy(Vertex source,int costLimit, int timeLimit){

        int costLimitCur =source.cost;
        int timeLimitCur =source.time;

        Queue<Vertex> frontier = new LinkedList<Vertex>();    // FIFO queue
        LinkedList<Vertex> explored = new LinkedList<>();
        frontier.add(source);
        if (timeLimitCur > timeLimit && costLimitCur> costLimit) {
            return  null;
        }
        while (true) {
            if (frontier.isEmpty()) {
                return explored;
            }
            Vertex state = frontier.poll();
            explored.add(state);
            timeLimitCur = timeLimitCur + state.time;
            costLimitCur = costLimitCur + state.cost;
            if (timeLimit >  timeLimitCur + state.time && costLimitCur + state.cost > costLimit) {
                return explored;
            }
            List<Edge> successors = state.adjacencies
                    ;
            Vertex maxVer = findMaxVertex(successors);
            if (!explored.contains(maxVer)) {
                frontier.add(maxVer);
            }

        }
    }
    public  Vertex findMaxVertex(List<Edge> edges){
        int maxV =0;
        Vertex maxVertex = new Vertex();
        for (Edge edge:edges) {
            if(edge.to.value > maxV){
                maxV = edge.to.value;
                maxVertex = edge.to;
            }
        }
        return maxVertex;
    }
}
