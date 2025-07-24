package com.ecommerce.backend.mapping;

import com.ecommerce.backend.dto.UserDTO;
import com.ecommerce.backend.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapping {



    public UserDTO toUserDTO(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone_number(user.getPhone_number());
        userDTO.setAddress(user.getAddress());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }
    public User toUser(UserDTO dto){
        User user=new User();
        user.setUserId(dto.getUserId());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setPhone_number(dto.getPhone_number());
        user.setAddress(dto.getAddress());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setCreatedAt(dto.getCreatedAt());
        return user;
    }
}
