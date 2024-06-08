package week13.board.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum UserRoleEnum implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
