package com.ecommerce.backend.service;

import com.ecommerce.backend.model.RefreshToken;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repositories.RefreshTokenRepository;
import com.ecommerce.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


public interface RefreshTokenService {



    public RefreshToken createRefreshToken(User user);

    public RefreshToken verifyExpiration(RefreshToken token);

    public Optional<RefreshToken> findByToken(String token);
}
