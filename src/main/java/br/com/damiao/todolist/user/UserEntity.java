package br.com.damiao.todolist.user;

public class UserEntity {
    private String username;
    private String name;
    private String password;

    UserEntity(String username, String name, String password){
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }
}
