package com.aprobapp.aprobapp.controller;

import com.aprobapp.aprobapp.model.User;
import com.aprobapp.aprobapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class UserController {
    private final UserService userService;
    public UserController (UserService userService){
        this.userService = userService;
    }
    @GetMapping("/users")
        public List<User> getAll(){
        return userService.getAllUsers();
    }
    @PostMapping("/users")
    public void add(@RequestBody User u){
        userService.addUser(u.getFirstName(), u.getSurName(), u.getEmail(),u.getPassword(), u.getStudy());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u){
        boolean valid = userService.validateLogin(u.getEmail(), u.getPassword());
        return valid ? ResponseEntity.ok("Login correcto") : ResponseEntity.status(401).body("Credenciales incorrectas");
    }
}
