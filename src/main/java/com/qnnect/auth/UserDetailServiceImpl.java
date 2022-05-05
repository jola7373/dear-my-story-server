package com.qnnect.auth;

import com.qnnect.auth.token.CurrentUserDetails;
import com.qnnect.common.exception.CustomException;
import com.qnnect.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CurrentUserDetails loadUserByUsername(String socialId) {
        return userRepository.findBySocialId(socialId)
                .map(u -> new CurrentUserDetails(u, Collections.singleton(new SimpleGrantedAuthority("USER"))))
                .orElseThrow(() -> new UserNotFoundException(socialId));
    }
}

