package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.service.LoginService;
import com.swm.studywithmentor.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public String authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        // No exception means login request is valid
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = (User) authentication.getPrincipal();
        return tokenProvider.generateToken(userDetails);
    }
}
