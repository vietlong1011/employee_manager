package com.o7planning.controller.SController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("index")
    public String hello(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // kiem tra tinh xac thuc cua user dang nhap
        model.addAttribute("authentication",authentication);// truyen thong tin doi tuong tu controller len view
        return "home";
    }

    @GetMapping("/user")
    public Map<String,Object> getUser(@AuthenticationPrincipal OAuth2User oAuth2User){ // annotation giup truy cap vao thong tin nguoi dung (luu vao tham so duoc chon , o day la OAuth2User)
        return oAuth2User.getAttributes();

    }
}
