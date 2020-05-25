package com.example.demo.controller;

import com.example.demo.mapper.ModelsMapper;
import com.example.demo.model.Graph;
import com.example.demo.model.GraphDto;
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
    ModelsMapper modelsMapper ;
    @Autowired
    PathService pathService;
    @Autowired
    InputService inputService;
    @RequestMapping(value = "/dejsktra",method = RequestMethod.POST)
    @ResponseBody
    public String dejkstraPathController(@RequestBody()  Graph graph ) {
        GraphDto graphDto = modelsMapper.mapper2(graph);
        List<Vertex> p = pathService.findPathServiceDejkstra(graphDto, 130, 190);
        return ModelsMapper.pathCreate(p);
    }
    @RequestMapping(value = "/greedy",method = RequestMethod.POST)

    public String greedy(@RequestBody()  Graph graph){
        GraphDto graphDto = modelsMapper.mapper2(graph);
        List<Vertex> p = pathService.findPathServiceGreedy(graphDto, 24, 27);
        return ModelsMapper.pathCreate(p);
    }
    @RequestMapping(value = "/dejsktra/random",method = RequestMethod.GET)

    public  List<Vertex>  randomGenerateDejkstra(){
        List<Vertex> p = pathService.findPathServiceDejkstra(inputService.generateService(4,4), 130, 190);
        return p;
    }
    @RequestMapping(value = "/greedy/random",method = RequestMethod.GET)

    public  String  randomGenerateGreedy(){
        GraphDto graphDto = inputService.generateService(6,6);
        List<Vertex> p = pathService.findPathServiceGreedy(graphDto, 130, 190);
       System.out.println("PATH  "+p);

        return  ModelsMapper.pathCreate(p);
    }
    @RequestMapping(value = "/f",method = RequestMethod.GET)

    public  GraphDto  randomGenerateDejkstrarr(){

        return inputService.generateService(6,6);
    }
}
