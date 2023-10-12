package br.com.damiao.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;
    
    @PostMapping("")
    public ResponseEntity create(@RequestBody TaskEntity taskEntity, HttpServletRequest request){
        if (taskEntity.getTitle().length() > 50) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("very long title.");
        }
        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskEntity.getStartAt()) || currentDate.isAfter(taskEntity.getEndAt())) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("The start/end date has passed.");
        }
        if (taskEntity.getStartAt().isAfter(taskEntity.getEndAt())) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("The end date is smaller than the start date.");
        }
        taskEntity.setUserId((UUID) request.getAttribute("userId"));
        var task = this.taskRepository.save(taskEntity);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(task);
    }
}
