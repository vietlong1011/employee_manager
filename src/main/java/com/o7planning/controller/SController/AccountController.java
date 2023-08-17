package com.o7planning.controller.SController;

import com.o7planning.dto.request.UserDTO;
import com.o7planning.entity.user.User;
import com.o7planning.service.UserService;
import com.o7planning.utils.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerAccount(userDTO));
    }



}
