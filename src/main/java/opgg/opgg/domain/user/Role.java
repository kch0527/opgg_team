package opgg.opgg.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
//
public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE,ADMIN");

    private final String value;
}
