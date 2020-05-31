package com.example.demo.services;

import com.example.demo.mapper.ModelsMapper;
import com.example.demo.model.Edge;
import com.example.demo.model.GraphDto;
import com.example.demo.model.Path;
import com.example.demo.model.Vertex;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathService {
    private int getVertex(GraphDto graphDto, Vertex ver) {
        for (int v = 0; v < graphDto.getVertices().size(); v++) {

            if (graphDto.getVertices().get(v).name.equals(ver.name)) {
                return v;
            }

        }
        return -2;
    }

    private List<Vertex> getPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {

            path.add(vertex);

        }

        Collections.reverse(path);
        return path;
    }
    private List<Vertex> getPathTo(Vertex target, Vertex source) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
            if (vertex.name.equals(source.name)){
               break;
            }


            //final Vertex v = vertex;
            //if(path.stream().filter(e -> e.name.equals(v.name)).count() > 0)
               // break;
           /*if (path.size()>10000){
               break;
           }

            */
        }
        Collections.reverse(path);
        return path;
    }

    public List<Vertex> findPathServiceDejkstra(GraphDto graph, int costLimit, int timeLimit, int i) {
        long startTime = System.currentTimeMillis();

        List<List<Vertex>> paths = new ArrayList<>();
        // computePaths(graph.vertices.get(1) ,graph);
        // paths.add( getShortestPathTo(graph.vertices.get(2)) );

        for (Vertex from :
                graph.getVertices()) {
            computePaths(from);
            //graph.setVertices( computePaths(from ,graph));
            for (Vertex to :
                    graph.getVertices()) {
                if (!from.equals(to)) {
                    List<Vertex> currentPath = getPathTo(to,from);

                    if (LimitsService.checkLimits(currentPath, costLimit, timeLimit)) {
                        paths.add(currentPath);
                    }
                }
            }
        }


        //  boolean f = LimitsService.checkLimits(paths.get(0), 20, 20);
        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println(i + " программа выполнялась  " + timeSpent + " миллисекунд"+ new Path(ModelsMapper.pathCreate(findPathWithMaxCost(paths)))+ countTargetFunction(findPathWithMaxCost(paths)));

        return findPathWithMaxCost(paths);
    }

    public List<Vertex> findPathServiceGreedy(GraphDto graph, int costLimit, int timeLimit, int i) {
        long startTime = System.currentTimeMillis();

        List<List<Vertex>> paths = new ArrayList<>();

        for (Vertex from :
                graph.getVertices()) {
            paths.add(greedy(from, costLimit, timeLimit));
        }
        long timeSpent = System.currentTimeMillis() - startTime;
      //   System.out.println(i + " программа выполнялась  " + timeSpent + " миллисекунд");
        return findPathWithMaxCost(paths);
    }


    private List<Vertex> findPathWithMaxCost(List<List<Vertex>> paths) {
        int maxCost = 0;
        List<Vertex> maxPath = new ArrayList<>();
        for (List<Vertex> path : paths) {
            int cost = calculateValue(path);
            if (cost > maxCost) {
                maxPath = path;
                maxCost = cost;
            }
        }
        return maxPath;
    }

    private int calculateValue(List<Vertex> path) {
        int val = 0;

        if(path!= null &&!path.isEmpty()) {
            for (Vertex v : path) {
                val = val + v.cost;
            }
        }
        return val;

    }

    public List<Vertex> greedy(Vertex source, int costLimit, int timeLimit) {

        int costLimitCur = source.cost;
        int timeLimitCur = source.time;

        Queue<Vertex> frontier = new LinkedList<Vertex>();    // FIFO queue
        LinkedList<Vertex> explored = new LinkedList<>();
        frontier.add(source);
        if (timeLimitCur > timeLimit && costLimitCur > costLimit) {
            return null;
        }
        while (true) {
            if (frontier.isEmpty()) {
                return explored;
            }
            Vertex state = frontier.poll();
            explored.add(state);
            timeLimitCur = timeLimitCur + state.time;
            costLimitCur = costLimitCur + state.cost;
            if (timeLimit > timeLimitCur + state.time && costLimitCur + state.cost > costLimit) {
                return explored;
            }
            List<Edge> successors = state.adjacencies;
            if (successors != null && !successors.isEmpty()) {
                Vertex maxVer = findMaxVertex(successors);
                if (!explored.contains(maxVer)) {
                    frontier.add(maxVer);
                }
            }
        }
    }

    public Vertex findMaxVertex(List<Edge> edges) {
        int maxV = 0;
        Vertex maxVertex = new Vertex();
        for (Edge edge : edges) {
            if (edge.to.value > maxV) {
                maxV = edge.to.value;
                maxVertex = edge.to;
            }
        }
        return maxVertex;
    }

    public int countTargetFunction(List<Vertex> p) {
        int value = 0;
        for (Vertex v : p) {
            value = value + v.value;
        }
        return value;
    }

    public int diff(List<Vertex> d, List<Vertex> g) {
        int dejkstrafunc = countTargetFunction(d);
        int greedyFunc = countTargetFunction(g);
        System.out.println("Dejkstra " + dejkstrafunc + " Greedy " + greedyFunc);
        return dejkstrafunc - greedyFunc;

    }

    public static void computePaths(Vertex source) {
        source.previous = null;
        source.maxValuePath = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        ArrayList<Vertex> explored = new ArrayList<>();
        HashSet<String> historyOfPrevious = new HashSet<>();
        vertexQueue.add(source);
        explored.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            
            // Visit each edge exiting u
            ArrayList<Edge> neigh = u.adjacencies;
            for (Edge e : neigh) {
                Vertex v = e.to;
                double distanceThroughU = u.maxValuePath + u.value;
                if (distanceThroughU > v.maxValuePath) {
                    vertexQueue.remove(v);
                    v.maxValuePath = distanceThroughU;
                    // explored.add(v);
                    boolean check =  v.name.equals(source.name);
                    if (u.previous != null && !u.previous.equals(v) && !check) {
                        //return to source
                        // VERtex can be previous once check history if contains go to next neigh
                        if(!historyOfPrevious.contains(u.name)) {
                            historyOfPrevious.add(u.name);
                            v.previous = u;


                        }
                        if (historyOfPrevious.contains(u.name)){
                            System.out.println(":");
                        }
                        else { continue;}
                    }
                    //    System.out.println("h");
                    if (!explored.contains(v)) {
                        explored.add(v);
                        vertexQueue.add(v); }

                }
            }
        }

        source.previous = null;
        source.maxValuePath = 0.;

    }
}
