package ro.ps.showmgmtbackend.dto.user;

import lombok.*;
import ro.ps.showmgmtbackend.model.Role;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private UUID userId;
    private String email;
    private String name;
    private Integer age;
    private Role role;
}
