package com.example.database_demo.controllers;

import com.example.database_demo.models.User;
import com.example.database_demo.repositories.UserRepository;
import com.example.database_demo.requests.UserRequerst;
import com.example.database_demo.responses.AllUsersResponseHandler;
import com.example.database_demo.responses.UsersResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public ResponseEntity<Object> addNewUser (@RequestBody UserRequerst userBody) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setName(userBody.getName());
        n.setEmail(userBody.getEmail());
        userRepository.save(n);
        return UsersResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, n);
    }

    @GetMapping(path="/all")
    public ResponseEntity<Object> getAllUsers() {
        // This returns a JSON or XML with the users
        List<User> allUsers = new ArrayList<User>();
        userRepository.findAll().forEach(allUsers::add);
        return AllUsersResponseHandler.generateResponse("Successfully fetched data!", HttpStatus.OK, allUsers);
    }
}