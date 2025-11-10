package com.aprobapp.aprobapp.controller;

import com.aprobapp.aprobapp.model.User;
import com.aprobapp.aprobapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin
public class UserController {
    private final UserService userService;
    public UserController (UserService userService){
        this.userService = userService;
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam String email){
        return ResponseEntity.ok(userService.getUserData(email));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAll(@RequestParam String email){
        if(!userService.isRootUser(email)){
            return ResponseEntity.status(403).body(Map.of("message","Acceso denegado: NO ERES ROOT"));}
            return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/create")
    public void add(@RequestBody User u){
        userService.addUser(u.getFirstName(), u.getSurName(), u.getEmail(),u.getPassword(), u.getStudy());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u){
        boolean valid = userService.validateLogin(u.getEmail(), u.getPassword());
       if(!valid){
           return ResponseEntity.status(401).body(Map.of("message", "Credenciales incorrectas"));
       }
       int rootValue = userService.getRootValue(u.getEmail());
        return ResponseEntity.ok(
                Map.of(
                        "message", "Login correcto",
                        "email", u.getEmail(),
                        "root", rootValue
                )
        );

    }
}
