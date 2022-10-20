package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.service.util.TokenUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TokenUtilTest {
    @Test
    public void getTokenTest() {
        assertTrue("Incorrect token", TokenUtil.getToken().matches("\\d{6}"));
    }
}
