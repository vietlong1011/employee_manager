package com.o7planning.controller;

import com.o7planning.entity.user.Email;
import com.o7planning.repository.URepository.EmailRepository;
import com.o7planning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/otp")
public class LoginWithOtpController {

    @Autowired
    private UserService userService;

    // click if forgot password -> send otp to email and change pass
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> updatePassword(@RequestParam String email, @RequestHeader String password) {
        return new ResponseEntity<>(userService.setPassword(email, password), HttpStatus.OK);
    }

    @GetMapping("/login-otp")
    public ResponseEntity<String> loginWithOTP(@RequestParam String otp) {
        return new ResponseEntity<>(userService.navigation(otp), HttpStatus.OK);
    }

    // reset otp
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
    }
}

