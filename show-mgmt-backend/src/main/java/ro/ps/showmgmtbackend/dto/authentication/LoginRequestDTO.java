package ro.ps.showmgmtbackend.dto.authentication;

import lombok.*;

/**
 * Represents a login
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    private String email;
    private String password;
}


