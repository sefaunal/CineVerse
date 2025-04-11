package com.sefaunal.cineverse.Request;

import com.sefaunal.cineverse.Annotation.EmailUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author github.com/sefaunal
 * @since 2025-04-10
 */
@Data
public class UserRequest {
    @NotNull
    @Size(min = 1, max = 75)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 75)
    private String lastName;

    @NotNull
    @EmailUpdate
    @Size(min = 2, max = 75)
    private String email;

    @NotNull
    @Size(min = 8, max = 75)
    private String password;
}