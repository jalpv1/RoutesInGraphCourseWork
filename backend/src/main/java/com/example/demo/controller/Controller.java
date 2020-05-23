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
    public List<Vertex> dejkstraPathController(@RequestBody()  Graph graph ) {
        GraphDto graphDto = modelsMapper.mapper(graph);
        List<Vertex> p = pathService.findPathServiceDejkstra(graphDto, 130, 190);
        return p;
    }
    @RequestMapping(value = "/greedy",method = RequestMethod.POST)

    public List<Vertex> greedy(@RequestBody()  Graph graph){
        GraphDto graphDto = modelsMapper.mapper(graph);
        List<Vertex> p = pathService.findPathServiceGreedy(graphDto, 130, 190);
        return p;
    }
    @RequestMapping(value = "/g",method = RequestMethod.GET)

    public GraphDto g(){

        return inputService.generateService(4,4);
    }
}
