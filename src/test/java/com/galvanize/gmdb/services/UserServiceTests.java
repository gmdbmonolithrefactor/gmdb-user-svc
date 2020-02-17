package com.galvanize.gmdb.services;

import com.galvanize.gmdb.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest@Transactional
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class UserServiceTests {

    @Autowired
    UserService service;

    private User testUser;

    @Before
    public void setup(){
        testUser = new User("user@email.com", "password", "username");
        service.save(testUser);
    }

    @Test
    public void save(){
        User user = new User("anotheruser@email.com", "password", "anotherusername");
        service.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void getUserByEmailAndPassword(){
        User user = service.getUser(testUser.getEmail(), testUser.getPassword());
        assertNotNull(user.getId());
        assertEquals(testUser.getId(), user.getId());
    }

    @Test
    public void getUserByEmailAndWrongPassword(){
        User user = service.getUser(testUser.getEmail(), testUser.getPassword()+"xxx");
        assertNull(user);
    }

    @Test
    public void getAllUsers(){
        for (int i = 0; i < 10; i++) {
            service.save(new User("User"+i+"@gmail.com", "password", "username"+i));
        }

        List<User> users = service.getAllUsers();
        assertTrue(users.size()>=10);
    }

    @Test
    public void updateUser(){
        String oldScreenName = testUser.getScreenName();
        testUser.setScreenName(oldScreenName+"123");
        service.save(testUser);
        User changedUser = service.getUser(testUser.getEmail(), testUser.getPassword());
        assertEquals(oldScreenName+"123", changedUser.getScreenName());
    }

    @Test
    public void updatePassword() {
        String newPassword = "newPassword";
        service.updatePassword(testUser.getId(), testUser.getPassword(), "newPassword");
        User user = service.getUser(testUser.getEmail(), newPassword);
        assertNotNull(user);
        assertEquals(testUser.getId(), user.getId());
    }

    @Test
    public void getUserById(){
        User user = service.getUser(testUser.getId());
        assertNotNull(user);
        assertEquals(testUser.getId(), user.getId());
    }

    @Test
    public void getUserById_badid() {
        User user = service.getUser(99999999l);
        assertNull(user);
    }
}
