package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.UserDTO;
import com.ecommerce.backend.model.Role;
import com.ecommerce.backend.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){

        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }


    @GetMapping("/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sortBy) {

        Page<UserDTO> usersPage = userService.getAllUsers(page, size, sortBy);

        Map<String, Object> response = new HashMap<>();
        response.put("content", usersPage.getContent());
        response.put("currentPage", usersPage.getNumber());
        response.put("totalPages", usersPage.getTotalPages());
        response.put("totalItems", usersPage.getTotalElements());
        response.put("start", Math.max(usersPage.getNumber() * usersPage.getSize() + 1, 0));
        response.put("end", Math.min((usersPage.getNumber() + 1) * usersPage.getSize(), (int) usersPage.getTotalElements()));
        response.put("sorted", usersPage.getSort().isSorted());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUser(id),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,@Valid @RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.updateUser(id,userDTO),HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public UserDTO getUserDetails(@RequestAttribute("username") String username) {
        // Get the user details by the userId from the JWT
        return userService.findByUsername(username);
    }

    @PutMapping("/me")
    public UserDTO updateUserDetails(@RequestAttribute("username") String username,@Valid @RequestBody UserDTO userDTO){
        UserDTO currentUser=userService.findByUsername(username);
        userDTO.setRole(Role.valueOf("USER"));
        return userService.updateUser(currentUser.getUserId(),userDTO);
    }
}
