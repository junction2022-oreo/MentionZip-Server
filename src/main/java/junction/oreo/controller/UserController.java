package junction.oreo.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import junction.oreo.dto.UserDto;
import junction.oreo.enums.response.CodeEnum;
import junction.oreo.response.CommonResponse;
import junction.oreo.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public CommonResponse<?> login(@Valid @RequestBody UserDto userDto) {
        return CommonResponse.builder()
                             .returnCode(CodeEnum.SUCCESS.getCode())
                             .info(userService.login(userDto))
                             .build();
    }

    @PostMapping("/signup")
    public CommonResponse<?> signup(@Valid @RequestBody UserDto userDto) {
        return CommonResponse.builder()
                             .returnCode(CodeEnum.SUCCESS.getCode())
                             .info(userService.signup(userDto))
                             .build();
    }
}
