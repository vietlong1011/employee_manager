package com.o7planning.service.impl;

import com.o7planning.dto.request.UserDTO;
import com.o7planning.entity.Provider;
import com.o7planning.entity.user.Email;
import com.o7planning.entity.user.Role;
import com.o7planning.entity.user.User;
import com.o7planning.repository.URepository.EmailRepository;
import com.o7planning.repository.URepository.RoleRepository;
import com.o7planning.repository.URepository.UserRepository;
import com.o7planning.service.UserService;
import com.o7planning.utils.BaseResponseDTO;
import com.o7planning.utils.email.EmailUtil;
import com.o7planning.utils.email.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final EmailRepository emailRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private OtpUtil otpUtil;

    @Autowired
    private EmailUtil emailUtil;


    @Override
    public BaseResponseDTO registerAccount(UserDTO userDTO) {

        BaseResponseDTO response = new BaseResponseDTO();
        Email email = new Email();

        User user = insertUser(userDTO);

        email.setEmailUser(userDTO.getEmail());
        email.setUser(user);
        try {
            userRepository.save(user);
            emailRepository.save(email);
            response.setCode(String.valueOf(HttpStatus.OK.value()));
            response.setMessage("Create account successfully");
        } catch (Exception e) {
            response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            response.setMessage("Service unavailable");
        }
        return response;

    }

    @Override
    public String forgotPassword(String email) {
        Email e = emailRepository.findByEmailUser(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmailSetPassword(email, otp); // gui OTP ve mail
        } catch (MessagingException ex) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        e.setOtp(otp);
        e.setOtpGeneratedTime(LocalDateTime.now());
        emailRepository.save(e);
        return "Email sent... please verify account within 1 minute";
    }


    @Override
    public String setPassword(String email, String newPassword) {

        emailRepository.findByEmailUser(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        Long findUser = emailRepository.findUserIdByNameEmail(email);
        User user = userRepository.findUserById(findUser);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "New password set successfully login with password";
    }

    // dieu huong sau khi co OTP
    @Override
    public String navigation(String otp) {
        Email email = emailRepository.findEmailByOtp(otp)
                .orElseThrow(() -> new RuntimeException("OTP not valid !!!"));
        Boolean isValid = verifyAccount(email.getEmailUser(), otp);

        if (Boolean.FALSE.equals(isValid)) {
            throw new RuntimeException("OTP not valid ");
        } else {
            return String.format("<div>" +"OTP is valid , u can login" +"<a href='http://localhost:8888/otp/set-password?email=%s' target='_blank'>click link to verify</a></div>",
                    email.getEmailUser());
        }
    }


    // check tinh hop le cua OTP (login)
    public Boolean verifyAccount(String email, String otp) {
        Email em = emailRepository.findByEmailUser(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (em.getOtp().equals(otp) && Duration.between(em.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)) {
            em.setActive(true);
            emailRepository.save(em);
            return true;
        }
        return false;
    }


    private User insertUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        // tim kiem trong DB xem co role trung voi role duoc them vao k
        roleSet.add(roleRepository.findByName(userDTO.getRole()));
        user.setRoles(roleSet);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        user.setProviderID(Provider.local.name());
        return user;
    }

    public String regenerateOtp(String email) {
        Email em = emailRepository.findByEmailUser(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmailSetPassword(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        em.setOtp(otp);
        em.setOtpGeneratedTime(LocalDateTime.now());
        emailRepository.save(em);
        return "Email sent... please verify account within 1 minute";
    }


}
