package br.com.damiao.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;
    
    @PostMapping("")
    public ResponseEntity create(@RequestBody TaskEntity taskEntity){
        if (taskEntity.getTitle().length() > 50) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("very long title.");
        }
        var task = this.taskRepository.save(taskEntity);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(task);
    }
}
