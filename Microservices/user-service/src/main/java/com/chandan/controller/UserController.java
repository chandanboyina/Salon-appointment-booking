package com.chandan.controller;

import com.chandan.exception.UserException;
import com.chandan.model.User;
import com.chandan.repository.UserRepository;
import com.chandan.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService  userService;

    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User createduser=userService.createUser(user);
        return new ResponseEntity<>(createduser, HttpStatus.CREATED);

    }

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.getUserFromJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUser(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) throws Exception {
        User user=userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) throws Exception {
        User updateUser=userService.updateUser(id, user);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);


    }
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws Exception {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User Deleted",HttpStatus.ACCEPTED);

    }
}
