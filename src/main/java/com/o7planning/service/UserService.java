package com.o7planning.service;

import com.o7planning.dto.request.UserDTO;
import com.o7planning.utils.BaseResponseDTO;

public interface UserService {

    BaseResponseDTO registerAccount(UserDTO userDTO);

    String regenerateOtp(String email);

    String forgotPassword(String email);

    String setPassword(String email , String newPassword);

    String navigation(String otp);
}
