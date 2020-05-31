package com.example.demo.controller;

import com.example.demo.mapper.ModelsMapper;
import com.example.demo.model.Graph;
import com.example.demo.model.GraphDto;
import com.example.demo.model.Path;
import com.example.demo.model.Vertex;
import com.example.demo.services.InputService;
import com.example.demo.services.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@CrossOrigin
public class Controller {
    @Autowired
    ModelsMapper modelsMapper ;
    @Autowired
    PathService pathService;
    @Autowired
    InputService inputService;
    @RequestMapping(value = "/dejsktra",method = RequestMethod.POST)
    @ResponseBody
    public Path dejkstraPathController(@RequestBody()  Graph graph ) {
        GraphDto graphDto = modelsMapper.mapper2(graph);
       List<Vertex> p = pathService.findPathServiceDejkstra(graphDto, graph.getCostLimit(), graph.getTimeLimit(),0);
        return new Path(ModelsMapper.pathCreate(p)+ " "+pathService.countTargetFunction(p));
    }
    @RequestMapping(value = "/greedy",method = RequestMethod.POST)
    @ResponseBody

    public Path greedy(@RequestBody()  Graph graph){
        GraphDto graphDto = modelsMapper.mapper2(graph);
        List<Vertex> p = pathService.findPathServiceGreedy(graphDto, graph.getCostLimit(), graph.getTimeLimit(),0);
        return new Path(ModelsMapper.pathCreate(p)+ " "+pathService.countTargetFunction(p));
    }
    @RequestMapping(value = "/dejsktra/random",method = RequestMethod.POST)
    @ResponseBody

    public  Path  randomGenerateDejkstra(@RequestParam int v,@RequestParam int e){

        List<Vertex> p = new ArrayList<>();
       for(int i = 5;i< 20;i=i+1) {
            long startTime = System.currentTimeMillis();

            GraphDto g = inputService.generateService(i,i);
            System.out.println("----------------------------------");
           for (Vertex ver: g.vertices) {
               System.out.println(ver);
           }
             p = pathService.findPathServiceDejkstra(g, (int) (Math.random() *100), (int) (Math.random() *100),i);
           System.out.println("----------------------------------");

      }

        return new Path( ModelsMapper.pathCreate(p));
    }
    @RequestMapping(value = "/greedy/random",method = RequestMethod.POST)
    @ResponseBody

    public   Path  randomGenerateGreedy(@RequestParam int v,@RequestParam int e){
        List<Vertex> p = new ArrayList<>();
        for(int i = 10;i< 1000;i=i+30) {
            GraphDto graphDto = inputService.generateService(i,i);

            p = pathService.findPathServiceGreedy(graphDto, (int) (Math.random() *100), (int) (Math.random() *100),i);
        }
            return  new Path(ModelsMapper.pathCreate(p));
    }
    @RequestMapping(value = "/f",method = RequestMethod.GET)

    public  GraphDto  randomGenerateDejkstrarr(){

        return inputService.generateService(6,6);
    }
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void testingController(){
        List<Vertex> pG = new ArrayList<>();
        List<Vertex> pD = new ArrayList<>();

        for (int i = 10;i <500;i=i+1) {
            System.out.println("Size "+i);
            GraphDto graphDto = InputService.generateService(i, i);
            System.out.println("1 "+1);

            pG = pathService.findPathServiceGreedy(graphDto, (int) (Math.random() *100), (int) (Math.random() *100),i);

            pD = pathService.findPathServiceDejkstra(graphDto, (int) (Math.random() *100), (int) (Math.random() *100),i);
          System.out.println("Size "+i+ " greedy path "+ModelsMapper.pathCreate(pG) +" dejkstra path "
                  +ModelsMapper.pathCreate(pD) + " diff "+ pathService.diff(pD,pG));


         //   System.out.println("Size "+i+ " diff "+ pathService.countTargetFunction(pD)+" "+ pathService.countTargetFunction(pG));

        }


    }
}
