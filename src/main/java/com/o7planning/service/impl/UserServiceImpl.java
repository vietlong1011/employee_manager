package com.o7planning.service.impl;

import com.o7planning.dto.request.UserDTO;
import com.o7planning.entity.Provider;
import com.o7planning.entity.user.Role;
import com.o7planning.entity.user.User;
import com.o7planning.repository.URepository.RoleRepository;
import com.o7planning.repository.URepository.UserRepository;
import com.o7planning.service.UserService;
import com.o7planning.utils.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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


}
