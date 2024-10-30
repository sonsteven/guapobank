package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private User user;

    private String username;
    private String password;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
        user = mock(User.class);

        when(user.getUsername()).thenReturn("testUser");
        when(user.getPassword()).thenReturn("testPassword");
    }

    @Test
    void TestSuccessfulAuthenticate() {
        String username = "testUser";
        String password = "testPassword";
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
        User authUser = userService.authenticate(username, password);

        verify(userRepository, times(1)).findByUsernameIgnoreCase(username);
        assertNotNull(authUser);
        assertEquals(user, authUser);
    }

    @Test
    void TestAuthenticateWrongPassword() {
        String username = "testUser";
        String password = "wrongPassword";
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
        User authUser = userService.authenticate(username, password);

        verify(userRepository, times(1)).findByUsernameIgnoreCase(username);
        assertNull(authUser);
    }

    @Test
    void TestAuthenticateInvalidUser() {
        String username = "invalidUser";
        String password = "testPassword";
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.empty());
        User result = userService.authenticate(username, password);
        assertNull(result);
    }

    @Test
    void TestFindAll() {
        userService.findAll();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void TestSave() {
        String username = "testUser";
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        verify(userRepository, times(1)).save(user);
        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
    }

    @Test
    void TestFindByIdSuccess() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findById(userId);

        verify(userRepository, times(1)).findById(userId);
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }
}
