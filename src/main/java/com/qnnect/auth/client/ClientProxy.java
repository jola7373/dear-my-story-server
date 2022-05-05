package com.qnnect.auth.client;

import com.qnnect.user.domain.User;

import java.security.NoSuchAlgorithmException;

public interface ClientProxy {
    User getUserData(String accessToken) throws NoSuchAlgorithmException;
}
