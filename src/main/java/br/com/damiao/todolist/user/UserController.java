package br.com.damiao.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @PostMapping("/signin")
    public void create(@RequestBody UserEntity userEntity){
        System.out.println(userEntity.getName());
    }
}
