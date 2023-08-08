package com.o7planning.controller.SController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

// page admin
@RestController
@RequestMapping("/admin")
public class AdminController {

    public ResponseEntity<String> index(Principal principal){
        return ResponseEntity.ok("Welcome to admin page : "+principal.getName());
    }
}
