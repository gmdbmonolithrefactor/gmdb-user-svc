package com.galvanize.gmdb.users.services;

import com.galvanize.gmdb.users.entities.User;
import com.galvanize.gmdb.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public User save(User user){
        return repository.save(user);
    }

    public User getUser(Long userid){
        Optional<User> user = repository.findById(userid);
        return user.isPresent() ? user.get() : null;
    }

    public User getUser(String email, String password){
        return repository.findUserByEmailAndPassword(email, password);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User updatePassword(long id, String oldPassword, String newPassword){
        Optional<User> oUser = repository.findById(id);
        if(oUser.isPresent() && oUser.get().getPassword().equals(oldPassword)){
            User u = oUser.get();
            u.setPassword(newPassword);
            repository.save(u);
            return u;
        }else{
            //TODO: Fix this!
            throw new RuntimeException("Old password doesn't match");
        }

    }
}
