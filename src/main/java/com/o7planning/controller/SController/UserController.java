package com.o7planning.controller.SController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/user")
public class UserController {

    public ResponseEntity<String> index(Principal principal){
        return ResponseEntity.ok("Welcome to user page "+ principal.getName());
    }
}
