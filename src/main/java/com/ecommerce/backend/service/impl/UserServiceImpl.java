package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.UserRepo;
import com.ecommerce.backend.dto.UserDTO;
import com.ecommerce.backend.mapping.UserMapping;
import com.ecommerce.backend.model.Role;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapping mapping;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setCreatedAt(LocalDateTime.now());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser=userRepo.save(mapping.toUser(userDTO));
        return mapping.toUserDTO(savedUser);
    }

    @Override
    public UserDTO deleteUser(Long userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User",userId,"User-ID"));
        userRepo.delete(user);
        return mapping.toUserDTO(user);
    }



    public Page<UserDTO> getAllUsers(Integer page, Integer size, String sortBy) {
        // Default values if parameters are null
        page = (page == null) ? 0 : page;
        size = (size == null) ? 10 : size;
        sortBy = (sortBy == null) ? "userId" : sortBy; // Default sorting by 'id'

        // Create a PageRequest with sorting
        Sort sort = Sort.by(sortBy).ascending(); // Ascending by default
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Fetch the paginated data from the database
        Page<User> userPage = userRepo.findAll(pageRequest);

        // Convert the Page<User> to Page<UserDTO> by mapping
        return userPage.map(mapping::toUserDTO);
    }

    public UserDTO getUserById(Long id){
        User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User",id,"User-ID"));
        return mapping.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user=mapping.toUser(getUserById(userId));
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setAddress(userDTO.getAddress());
        user.setPhone_number(userDTO.getPhone_number());
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail(userDTO.getEmail());

        return mapping.toUserDTO(userRepo.save(user));
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        userDTO.setCreatedAt(LocalDateTime.now());
        userDTO.setRole(Role.valueOf("USER"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser=userRepo.save(mapping.toUser(userDTO));
        return mapping.toUserDTO(savedUser);
    }

    public UserDTO findByUsername(String username){
        User user=userRepo.findByUsername(username).get();
        return mapping.toUserDTO(user);
    }

}
