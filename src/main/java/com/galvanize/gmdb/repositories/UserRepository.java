package com.galvanize.gmdb.repositories;

import com.galvanize.gmdb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findUserByEmailAndPassword(String email, String password);
}
