package opgg.opgg.web.dto.user;

import lombok.*;
import opgg.opgg.domain.user.User;
import opgg.opgg.domain.user.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    // DTO -> Entity
    public User toEntity() {
        User user = User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .role(role.USER)
                .build();
        return user;
    }

}
