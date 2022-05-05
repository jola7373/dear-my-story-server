package com.qnnect.fixture;

import com.qnnect.cafe.domain.Cafe;

public class CafeFixture {
    public static final Cafe TEST_CAFE = Cafe.builder().code("ABC12OP2")
            .title("Testing").build();
}
