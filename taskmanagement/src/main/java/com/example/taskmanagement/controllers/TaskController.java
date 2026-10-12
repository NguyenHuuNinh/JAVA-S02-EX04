package com.example.taskmanagement.controllers;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) String search) {
        List<Task> tasks = taskService.findAllTasks();
        if (search != null && !search.trim().isEmpty()) {
            tasks = tasks.stream()
                    .filter(task -> task.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(tasks);
    }


    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task newTask) {
        Task createdTask = taskService.createTask(newTask);

        if (createdTask != null) {
            // Nếu thêm thành công, trả về mã 201 Created cùng dữ liệu Task
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } else {
            // Nếu thất bại (User không tồn tại), trả về mã 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Không tìm thấy User với ID là " + newTask.getAssignedTo());
        }
    }
}