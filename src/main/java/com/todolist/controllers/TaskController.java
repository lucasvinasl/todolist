package com.todolist.controllers;

import com.todolist.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.todolist.services.TaskServices;
import com.todolist.services.UserServices;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @Autowired
    private UserServices userServices;


    @GetMapping("/{id}")
    public ResponseEntity<Task> byId(@PathVariable Long id){
        Task task = this.taskServices.findTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task task){
        this.taskServices.createTask(task);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }


    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task task,
                                       @PathVariable Long id){
        task.setId(id);
        this.taskServices.updateTask(task);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.taskServices.deteleTask(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllTasksByUserId(@PathVariable Long userId){
        this.userServices.findUserById(userId);
        List<Task> tasks = this.taskServices.findAllByUserId(userId);
        return ResponseEntity.ok().body(tasks);
    }



}
