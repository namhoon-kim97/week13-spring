package week13.board.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import week13.board.domain.User;
import week13.board.dto.UserDto;
import week13.board.exception.CustomException;
import week13.board.exception.ErrorCode;
import week13.board.repository.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(User.Role.USER)
                .build();

        return UserDto.from(userRepository.save(user));
    }
}
