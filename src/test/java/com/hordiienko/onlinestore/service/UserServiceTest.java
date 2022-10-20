package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService mockUserService;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private User mockUser;

    @Before
    public void createUser() {
        mockUser = new User();
        mockUser.setUsername("user1@mmm.com");
        mockUser.setPassword("qwe123");
    }

    @Test
    public void registrationNewUserTest() {
        when(mockUserRepository.existsByUsername(anyString())).thenReturn(false);
        when(mockUserRepository.save(mockUser)).thenReturn(mockUser);
        doNothing().when(emailSenderService).sendMessageRegistered(mockUser);

        mockUserService.registrationUser(mockUser, Locale.getDefault());
    }

    @Test(expected = UserAlreadyExistException.class)
    public void registrationExistingUser() {
        when(mockUserRepository.existsByUsername(anyString())).thenReturn(true);
        mockUserService.registrationUser(mockUser, Locale.getDefault());
    }
}