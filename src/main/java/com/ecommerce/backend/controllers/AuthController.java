package com.ecommerce.backend.controllers;


import com.ecommerce.backend.dto.UserDTO;
import com.ecommerce.backend.model.*;
import com.ecommerce.backend.security.JwtHelper;
import com.ecommerce.backend.service.RefreshTokenService;
import com.ecommerce.backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController()
@Tag(name = "Authorization")
public class AuthController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @Autowired
    private UserService userServ;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@Valid @RequestBody JwtRequest request){
        this.authenticate(request.getUsername(),request.getPassword());

        UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());

        String token=jwtHelper.generateToken(userDetails);

        User user=(User) userDetails;
        RefreshToken refreshToken= tokenService.createRefreshToken(user);
        JwtResponse response=new JwtResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken.getToken());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        // Register the user
        UserDTO registeredUser = userService.registerUser(userDTO);

        // Return the registered user details
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }


    private void authenticate(String username,String password){
        UsernamePasswordAuthenticationToken usernamePasswordToken=new UsernamePasswordAuthenticationToken(username,password);
        try {
            this.authenticationManager.authenticate(usernamePasswordToken);
        }catch (DisabledException e){
            throw new DisabledException("User is disabled...");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return tokenService.findByToken(requestRefreshToken)
                .map(tokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtHelper.generateToken(user);
                    return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

}
