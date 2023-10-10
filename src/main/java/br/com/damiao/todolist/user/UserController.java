package br.com.damiao.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    
    @PostMapping("/signin")
    public ResponseEntity create(@RequestBody UserEntity userEntity){
        var user = this.userRepository.findByUsername(userEntity.getUsername());
        if (user != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("user already exists.");
        }
        UserEntity userCreated = this.userRepository.save(userEntity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userCreated);
    }
}
