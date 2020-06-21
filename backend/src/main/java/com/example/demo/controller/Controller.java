package com.example.demo.controller;

import com.example.demo.mapper.ModelsMapper;
import com.example.demo.model.Graph;
import com.example.demo.model.GraphDto;
import com.example.demo.model.Path;
import com.example.demo.model.Vertex;
import com.example.demo.services.InputService;
import com.example.demo.services.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@CrossOrigin
public class Controller {
    @Autowired
    ModelsMapper modelsMapper;
    @Autowired
    PathService pathService;
    @Autowired
    InputService inputService;

    @RequestMapping(value = "/dejsktra", method = RequestMethod.POST)
    @ResponseBody
    public Path dejkstraPathController(@RequestBody() Graph graph) {
        GraphDto graphDto = modelsMapper.mapper2(graph);
        List<Vertex> p = pathService.findPathServiceDejkstra(graphDto, graph.getCostLimit(), graph.getTimeLimit(), 0);
        return new Path("First algorithm " + ModelsMapper.pathCreate(p) + " Target function " + pathService.countTargetFunction(p));
    }

    @RequestMapping(value = "/greedy", method = RequestMethod.POST)
    @ResponseBody
    public Path greedy(@RequestBody() Graph graph) {
        GraphDto graphDto = modelsMapper.mapper2(graph);
        List<Vertex> p = pathService.findPathServiceGreedy(graphDto, graph.getCostLimit(), graph.getTimeLimit(), 0);
        return new Path("Second algorithm " + ModelsMapper.pathCreate(p) + " Target function " + pathService.countTargetFunction(p));
    }

    @RequestMapping(value = "/dejsktra/random", method = RequestMethod.POST)
    @ResponseBody

    public Path randomGenerateDejkstra(@RequestParam int v, @RequestParam int e) {
        List<Vertex> p;
        GraphDto g = inputService.generateService(v, e);
        System.out.println("----------------------------------");
        for (Vertex ver : g.vertices) {
            System.out.println(ver);
        }
        p = pathService.findPathServiceDejkstra(g, (int) (Math.random() * 100), (int) (Math.random() * 100), 0);
        System.out.println("----------------------------------");
        return new Path(ModelsMapper.pathCreate(p));
    }

    @RequestMapping(value = "/test/dejsktra/random", method = RequestMethod.POST)
    @ResponseBody

    public Path randomGenerateDejkstraStat(@RequestParam int v, @RequestParam int e) {

        List<Vertex> p = new ArrayList<>();
        for (int i = 5; i < 50; i = i + 1) {
            GraphDto g = inputService.generateService(i, i);
            System.out.println("----------------------------------");
            for (Vertex ver : g.vertices) {
                System.out.println(ver);
            }
            p = pathService.findPathServiceDejkstra(g, (int) (Math.random() * 100), (int) (Math.random() * 100), i);
            System.out.println("----------------------------------");

        }
        return new Path(ModelsMapper.pathCreate(p));
    }

    @RequestMapping(value = "/greedy/random", method = RequestMethod.POST)
    @ResponseBody

    public Path randomGenerateGreedy(@RequestParam int v, @RequestParam int e) {
        List<Vertex> p ;
        GraphDto graphDto = inputService.generateService(v, e);
        for (Vertex ver : graphDto.vertices) {
            System.out.println(ver);
        }
        p = pathService.findPathServiceGreedy(graphDto, (int) (Math.random() * 100), (int) (Math.random() * 100), 0);
        return new Path(ModelsMapper.pathCreate(p));
    }

    @RequestMapping(value = "/test/greedy/random", method = RequestMethod.POST)
    @ResponseBody

    public Path randomGenerateGreedyStat(@RequestParam int v, @RequestParam int e) {
        List<Vertex> p = new ArrayList<>();
        for (int i = 10; i < 1000; i = i +30) {
            GraphDto graphDto = inputService.generateService(i, i);
            for (Vertex ver : graphDto.vertices) {
                System.out.println(ver);
            }

            p = pathService.findPathServiceGreedy(graphDto, (int) (Math.random() * 100), (int) (Math.random() * 100), i);
        }
        return new Path(ModelsMapper.pathCreate(p));
    }

    @RequestMapping(value = "/test/random", method = RequestMethod.GET)
    public GraphDto randomTest() {
        return inputService.generateService(6, 6);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testingController() {
        List<Vertex> pG = new ArrayList<>();
        List<Vertex> pD = new ArrayList<>();

        for (int i = 5; i < 30; i = i + 1) {
            System.out.println("Size " + i);
            GraphDto graphDto = InputService.generateService(i, i);
            for (Vertex ver : graphDto.vertices) {
                System.out.println(ver);
            }
                   pG = pathService.findPathServiceGreedy(graphDto, (int) (Math.random() *100), (int) (Math.random() *100),i);
          //  pG = pathService.findPathServiceGreedy(graphDto, 0, 0, i);
                 pD = pathService.findPathServiceDejkstraNodes(graphDto, (int) (Math.random() *100), (int) (Math.random() *100),i);
            //
           // pD = pathService.findPathServiceDejkstraNodes(graphDto, 0, 0, i);
            System.out.println("Size "+i+ " greedy path "+ModelsMapper.pathCreate(pG) +" dejkstra path "
              +ModelsMapper.pathCreate(pD) + " diff "+ pathService.diff(pD,pG));

            //System.out.println("Size " + i + " diff " + pathService.diff(pD, pG));

        }


    }
}
