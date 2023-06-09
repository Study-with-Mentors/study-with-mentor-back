package com.swm.studywithmentor.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.swm.studywithmentor.model.entity.Image;
import com.swm.studywithmentor.model.entity.user.Role;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.AccountLockedException;
import com.swm.studywithmentor.model.exception.GoogleIdTokenVerificationFailedException;
import com.swm.studywithmentor.model.exception.PasswordNotSetException;
import com.swm.studywithmentor.model.exception.UserNotVerifiedExceptions;
import com.swm.studywithmentor.model.exception.WrongEmailOrPasswordException;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.LoginService;
import com.swm.studywithmentor.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Value("${google.client-id}")
    private String CLIENT_ID;
    private GoogleIdTokenVerifier verifier;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager,
                            JwtTokenProvider tokenProvider,
                            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
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
        try {
            userRepository.findByEmail(email)
                    .ifPresentOrElse(
                            user -> {
                                if (user.getPassword() == null) {
                                    throw new PasswordNotSetException(email);
                                }
                            },
                            () -> {
                                throw new BadCredentialsException("Bad credentials");
                            }
                    );

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // No exception means login request is valid
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User userDetails = (User) authentication.getPrincipal();
            return tokenProvider.generateToken(userDetails);
        } catch (DisabledException e) {
            throw new UserNotVerifiedExceptions(email, e);
        } catch (BadCredentialsException e) {
            throw new WrongEmailOrPasswordException(email, e);
        } catch (AccountStatusException e) {
            throw new AccountLockedException(email, e);
        }
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

            // If email is not in database, create new user
            User userDetails = userRepository.findByEmail(email)
                    .orElseGet(() -> createUserFromGoogle(payload));

            // If account is not verify, enable it because email was verified by Google
            if (!userDetails.isEnabled()) {
                userDetails.setEnabled(true);
                userRepository.save(userDetails);
            }
            // Check if account is not locked
            if (userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked()
                    && userDetails.isCredentialsNonExpired()) {
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return tokenProvider.generateToken(userDetails);
            }

            // Account cannot be accessed
            throw new AccountLockedException(userDetails);
        } catch (GeneralSecurityException | IOException e) {
            throw new GoogleIdTokenVerificationFailedException(idTokenString, e);
        }
    }

    private User createUserFromGoogle(GoogleIdToken.Payload payload) {
        Image avatar = new Image();
        avatar.setUrl((String) payload.get("picture"));

        User user = User.builder()
                .email(payload.getEmail())
                .firstName((String) payload.get("given_name"))
                .lastName((String) payload.get("family_name"))
                .profileImage(avatar)
                .enabled(true)
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }
}
