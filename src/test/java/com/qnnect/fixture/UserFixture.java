package com.qnnect.fixture;

import com.qnnect.user.domain.User;

import java.util.UUID;

public class UserFixture {
    public static final User TEST_USER = User.builder().id(UUID.randomUUID())
            .nickName("TESTing").build();
}
