package com.ecommerce.backend.model;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
