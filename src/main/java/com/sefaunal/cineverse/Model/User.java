package com.sefaunal.cineverse.Model;

import com.sefaunal.cineverse.Annotation.UniqueEmail;
import com.sefaunal.cineverse.Annotation.UniqueUsername;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

/**
 * @author github.com/sefaunal
 * @since 2025-01-11
 */
@Data
@Document
public class User implements UserDetails {
    @Id
    private String ID;

    @NotNull
    @Size(min = 1, max = 75)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 75)
    private String lastName;

    @NotNull
    @UniqueUsername
    @Size(min = 2, max = 75)
    private String username;

    @NotNull
    @UniqueEmail
    @Size(min = 2, max = 75)
    private String email;

    @NotNull
    @Size(min = 8, max = 75)
    private String password;

    private String role;

    private String profileImageURI;

    private Instant accountCreationDate;

    private boolean isAccountNonLocked;

    private boolean isAccountEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountEnabled;
    }
}