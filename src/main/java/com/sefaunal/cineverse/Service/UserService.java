package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2025-01-11
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + username));
    }

    public User findUserByID(String ID) {
        return userRepository.findById(ID)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + ID));
    }

    public boolean isEmailFree(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public boolean isUsernameFree(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    public void createUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole("USER");
        user.setAccountCreationDate(Instant.now());
        user.setAccountNonLocked(true);
        user.setAccountEnabled(true);
        user.setProfileImageURI("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Windows_10_Default_Profile_Picture.svg/2048px-Windows_10_Default_Profile_Picture.svg.png");
        userRepository.save(user);
    }

    public Page<User> findByRoleWithPageable(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    public void grantAdminPrivileges(User user) {
        user.setRole("ADMIN");
        userRepository.save(user);
    }

    public void revokeAdminPrivileges(User user) {
        user.setRole("USER");
        userRepository.save(user);
    }

    public void banUser(User user) {
        user.setRole("BANNED");
        user.setAccountNonLocked(false);
        user.setAccountEnabled(false);

        userRepository.save(user);
    }

    public void unbanUser(User user) {
        user.setRole("USER");
        user.setAccountNonLocked(true);
        user.setAccountEnabled(true);

        userRepository.save(user);
    }
}