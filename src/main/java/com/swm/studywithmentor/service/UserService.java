package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getCurrentUser();
}
