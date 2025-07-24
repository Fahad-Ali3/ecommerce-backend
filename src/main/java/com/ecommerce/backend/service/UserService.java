package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO deleteUser(Long userId);
    Page<UserDTO> getAllUsers(Integer pageSize, Integer size, String sortBy);
    UserDTO getUserById(Long userId);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    UserDTO registerUser(UserDTO userDTO);
}
