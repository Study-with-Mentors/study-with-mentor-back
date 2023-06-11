package com.swm.studywithmentor.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.AccountLockedException;
import com.swm.studywithmentor.model.exception.GoogleIdTokenVerificationFailedException;
import com.swm.studywithmentor.model.exception.ValidEmailNotInDatabaseException;
import com.swm.studywithmentor.service.LoginService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Value("${google.client-id}")
    private String CLIENT_ID;
    private GoogleIdTokenVerifier verifier;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
        UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostConstruct
    public void initVerifier() {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList(CLIENT_ID))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build();
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

    @Override
    public String authenticateGoogle(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new GoogleIdTokenVerificationFailedException(idTokenString);
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            try {
                UserDetails userDetails = userService.loadUserByUsername(email);

                // If email is valid, check account's status
                if (userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked()
                    && userDetails.isCredentialsNonExpired() && userDetails.isEnabled()) {
                    UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    return tokenProvider.generateToken(userDetails);
                }

                // Account cannot be accessed
                throw new AccountLockedException((User) userDetails);
            } catch (UsernameNotFoundException e) {
                // Email is valid but account doesn't exist in database,
                // should redirect to register page.
                throw new ValidEmailNotInDatabaseException(email, e);
            }

        } catch (GeneralSecurityException | IOException e) {
            throw new GoogleIdTokenVerificationFailedException(idTokenString, e);
        }
    }
}
