package com.o7planning.service;

import com.o7planning.dto.request.UserDTO;
import com.o7planning.utils.BaseResponseDTO;

public interface UserService {

    BaseResponseDTO registerAccount(UserDTO userDTO);
}
