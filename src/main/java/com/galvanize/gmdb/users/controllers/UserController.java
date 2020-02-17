package com.galvanize.gmdb.users.controllers;

import com.galvanize.gmdb.users.entities.User;
import com.galvanize.gmdb.users.rest.AuthRequest;
import com.galvanize.gmdb.users.rest.UpdatePasswordRequest;
import com.galvanize.gmdb.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/gmdb/api/user")
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

    @GetMapping("")
    public User getUser(@RequestParam(name = "email") String email, @RequestParam(name = "pwd") String password){
        return service.getUser(email, password);
    }

    @PostMapping("/auth")
    public boolean authentic(@RequestBody AuthRequest request, HttpServletResponse response){
        User user = service.getUser(request);
        if (user == null) response.setStatus(HttpStatus.UNAUTHORIZED.value());
        else response.setStatus(HttpStatus.OK.value());
        return user != null;
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
