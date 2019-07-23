package com.galvanize.gmdb.users.repositories;

import com.galvanize.gmdb.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findUserByEmailAndPassword(String email, String password);
}
