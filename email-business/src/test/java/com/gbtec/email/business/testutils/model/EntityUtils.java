package com.gbtec.email.business.testutils.model;

import java.security.SecureRandom;

public class EntityUtils {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static Long randomLongId() {
        return RANDOM.nextLong(1L, 999999L);
    }

    private EntityUtils() {
    }
}
