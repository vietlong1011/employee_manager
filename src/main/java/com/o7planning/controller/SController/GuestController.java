package com.o7planning.controller.SController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// trang public
@RestController
@RequestMapping("/quest")
public class GuestController {

    public ResponseEntity<String> index(){
        return ResponseEntity.ok("Welcome");
    }
}