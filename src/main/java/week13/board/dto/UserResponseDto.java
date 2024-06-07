package week13.board.dto;

import lombok.*;
import week13.board.domain.User;
import week13.board.domain.UserRoleEnum;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private UserRoleEnum role;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
