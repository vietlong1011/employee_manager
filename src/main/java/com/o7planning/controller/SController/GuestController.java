package com.o7planning.controller.SController;

import com.o7planning.dto.request.UserDTO;
import com.o7planning.entity.user.Role;
import com.o7planning.entity.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

// trang public
@RestController
@RequestMapping("/guest")
public class GuestController {

    @GetMapping("/index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("Welcome");
    }

    @GetMapping("/status")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        // Trả về kết quả
        return ResponseEntity.ok(authentication.getPrincipal());
    }
}

