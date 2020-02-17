package com.galvanize.gmdb.controllers;

import com.galvanize.gmdb.entities.User;
import com.galvanize.gmdb.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest @Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.properties")
public class UserControllerTests {
    @Autowired
    UserService service;

    @Autowired
    MockMvc mvc;

    private String BASE_URI="/gmdb/api/user";
    private User testUser;

    @Before
    public void setup() {
        testUser  = new User("me@email.com", "password", "screename");
        service.save(testUser);
        assertNotNull(testUser.getId());
    }

    @Test
    public void authenticatePass() throws Exception{
        String json = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }",
                testUser.getEmail(), testUser.getPassword());
        MockHttpServletRequestBuilder request = post(BASE_URI+"/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void authenticateFail() throws Exception{
        String json = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }",
                testUser.getEmail(), testUser.getPassword()+"bad");
        MockHttpServletRequestBuilder request = post(BASE_URI+"/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

//    @Test // TODO: NEED SECURITY FOR THIS ENDPOINT
//    public void findAllUsers() throws Exception {
//        mvc.perform(get(BASE_URI+"/all"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

    @Test
    public void findUserById() throws Exception {
        mvc.perform(get(BASE_URI+"/"+testUser.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testUser.getId().intValue())));
    }

    @Test
    public void getUserByEmailAndPassword() throws Exception {
        mvc.perform(get(BASE_URI).param("email", testUser.getEmail()).param("pwd", testUser.getPassword()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testUser.getId().intValue())));
    }

    @Test
    public void createUser() throws Exception{
        String json = "{\"email\":\"newuser@gmdb.com\",\"password\":\"password\",\"screenName\":\"newScreenName\"}";
        MockHttpServletRequestBuilder request = post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    public void updateUser() throws Exception {
        String json = String.format("{\"id\":\"%s\",\"email\":\"newuser@gmdb.com\",\"password\":\"password\",\"screenName\":\"newScreenName\"}", testUser.getId());
        MockHttpServletRequestBuilder request = post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testUser.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.screenName", is("newScreenName")));
    }
}
