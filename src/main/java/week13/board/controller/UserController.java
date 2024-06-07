package week13.board.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import week13.board.constants.ResponseMessage;
import week13.board.domain.User;
import week13.board.dto.ApiResponse;
import week13.board.dto.LoginRequestDto;
import week13.board.dto.RegisterRequestDto;
import week13.board.dto.UserResponseDto;
import week13.board.service.UserService;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> signup(RegisterRequestDto requestDto) {
        userService.register(requestDto);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CREATED.value(), ResponseMessage.USER_REGISTER_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
        User user = userService.login(requestDto, httpServletResponse);
        ApiResponse<UserResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.USER_LOGIN_SUCCESS, UserResponseDto.of(user));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
