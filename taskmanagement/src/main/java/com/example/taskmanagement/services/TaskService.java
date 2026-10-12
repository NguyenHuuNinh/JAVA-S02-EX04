package com.example.taskmanagement.services;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService; // Khai báo UserService

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService; // Tiêm UserService vào thông qua Constructor
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    // Hàm tạo Task mới có kiểm tra User hợp lệ
    public Task createTask(Task newTask) {
        // Tìm kiếm xem User có ID bằng assignedTo có tồn tại không
        User user = userService.findUserById(newTask.getAssignedTo());

        if (user != null) {
            // Nếu tìm thấy User -> Lưu Task và trả về dữ liệu Task
            return taskRepository.save(newTask);
        }
        // Nếu không tìm thấy User -> Trả về null
        return null;
    }
}