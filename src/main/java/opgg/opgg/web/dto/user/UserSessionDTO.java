package opgg.opgg.web.dto.user;

import lombok.Getter;
import opgg.opgg.domain.user.User;
import opgg.opgg.domain.user.Role;

/**
 * 인증된 사용자 정보를 세션에 저장하기 위한 클래스
 */
@Getter
public class UserSessionDTO {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    // Entity -> DTO
    public UserSessionDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
