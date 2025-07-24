package com.ecommerce.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
