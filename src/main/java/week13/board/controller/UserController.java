package week13.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import week13.board.constants.ResponseMessage;
import week13.board.dto.ApiResponse;
import week13.board.dto.UserDto;
import week13.board.service.UserService;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signup(@Valid @RequestBody UserDto userDto) {
        ApiResponse<UserDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), ResponseMessage.USER_REGISTER_SUCCESS, userService.signup(userDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<UserResponseDto>> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
//        User user = userService.login(requestDto, httpServletResponse);
//        ApiResponse<UserResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.USER_LOGIN_SUCCESS, UserResponseDto.of(user));
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
