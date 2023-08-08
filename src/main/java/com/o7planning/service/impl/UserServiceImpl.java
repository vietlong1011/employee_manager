package com.o7planning.service.impl;

import com.o7planning.dto.UserDTO;
import com.o7planning.entity.user.Role;
import com.o7planning.entity.user.User;
import com.o7planning.exception.BaseException;
import com.o7planning.repository.URepository.RoleRepository;
import com.o7planning.repository.URepository.UserRepository;
import com.o7planning.service.UserService;
import com.o7planning.utils.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public BaseResponseDTO registerAccount(UserDTO userDTO) {

        BaseResponseDTO response = new BaseResponseDTO();

        validateAccount(userDTO);

        User user = insertUser(userDTO);

        try {
            userRepository.save(user);
            response.setCode(String.valueOf(HttpStatus.OK.value()));
            response.setMessage("Create account successfully");
        } catch (Exception e) {
            response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            response.setMessage("Service unavailable");
        }
        return response;

    }

    private User insertUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByName(userDTO.getUsername()));
        user.setRoles(roleSet);
        return user;
    }

    private void validateAccount(UserDTO userDTO) {
        // validate null data
        if (ObjectUtils.isEmpty(userDTO)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Data user not null");
        }

        //validate username exist
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (!ObjectUtils.isEmpty(user)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST), "username has existed");
        }

        //validate role (check xem role co ton tai trong bang role cho phep k)
        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
        if (!roles.contains(userDTO.getRole())) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST), "Invalid role");
        }

    }
}
