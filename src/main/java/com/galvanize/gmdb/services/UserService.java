package com.galvanize.gmdb.services;

import com.galvanize.gmdb.entities.User;
import com.galvanize.gmdb.repositories.UserRepository;
import com.galvanize.gmdb.rest.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

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

    public User getUser(AuthRequest request){
        return getUser(request.getUsername(), request.getPassword());
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
