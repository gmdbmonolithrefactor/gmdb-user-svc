package com.galvanize.gmdb.users.controllers;

import com.galvanize.gmdb.users.entities.User;
import com.galvanize.gmdb.users.rest.UpdatePasswordRequest;
import com.galvanize.gmdb.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gmdb/api/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/{userid}")
    public User getUserById(@PathVariable Long userid) {
        return service.getUser(userid);
    }
    @GetMapping("/")
    public User getUser(@RequestParam(name = "email") String email, @RequestParam(name = "pwd") String password){
        return service.getUser(email, password);
    }

    @PostMapping("")
    public User createUser(@RequestBody User user){
        return service.save(user);
    }

    @PutMapping("")
    public User updateUser(@RequestBody UpdatePasswordRequest updatePassword){
        return service.updatePassword(updatePassword.getId(), updatePassword.getOldPassword(), updatePassword.getNewPassword());
    }

}
