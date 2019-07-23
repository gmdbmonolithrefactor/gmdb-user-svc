package com.galvanize.gmdb.users.services;

import com.galvanize.gmdb.users.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest@Transactional
@RunWith(SpringRunner.class)
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
    public void save() throws Exception{
        User user = new User("anotheruser@email.com", "password", "anotherusername");
        service.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void getUserByEmailAndPassword() throws Exception{
        User user = service.getUser(testUser.getEmail(), testUser.getPassword());
        assertNotNull(user.getId());
        assertEquals(testUser.getId(), user.getId());
    }

    @Test
    public void getUserByEmailAndWrongPassword() throws Exception {
        User user = service.getUser(testUser.getEmail(), testUser.getPassword()+"xxx");
        assertTrue(user == null);
    }

    @Test
    public void getAllUsers() throws Exception {
        for (int i = 0; i < 10; i++) {
            service.save(new User("User"+i+"@gmail.com", "password", "username"+i));
        }

        List<User> users = service.getAllUsers();
        assertTrue(users.size()>=10);
    }

    @Test
    public void updateUser() throws Exception {
        String oldScreenName = testUser.getScreenName();
        testUser.setScreenName(oldScreenName+"123");
        service.save(testUser);
        User changedUser = service.getUser(testUser.getEmail(), testUser.getPassword());
        assertEquals(oldScreenName+"123", changedUser.getScreenName());
    }

    @Test
    public void updatePassword() throws Exception {
        String newPassword = "newPassword";
        service.updatePassword(testUser.getId(), testUser.getPassword(), "newPassword");
        User user = service.getUser(testUser.getEmail(), newPassword);
        assertNotNull(user);
        assertEquals(testUser.getId(), user.getId());
    }

    @Test
    public void getUserById() throws Exception {
        User user = service.getUser(testUser.getId());
        assertNotNull(user);
        assertEquals(testUser.getId(), user.getId());
    }

    @Test
    public void getUserById_badid() throws Exception {
        User user = service.getUser(99999999l);
        assertNull(user);
    }
}
