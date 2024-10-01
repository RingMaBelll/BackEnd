package com.ringmabell.whichme_backend.controller;

import com.ringmabell.whichme_backend.dto.JoinDto;
import com.ringmabell.whichme_backend.response.ErrorResponse;
import com.ringmabell.whichme_backend.response.Response;
import com.ringmabell.whichme_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "UserController")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "일반유저 회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이메일 중복",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "아이디 중복",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "형식오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Response> join(@RequestBody @Valid JoinDto joinDto) {
        return ResponseEntity.ok().body(userService.saveUser(joinDto));
    }

    @GetMapping("/validate/username")   // 아이디 중복확인
    @Operation(summary = "유저 id 사용가능 여부 확인 API")
    public ResponseEntity<Response> validateUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(userService.isAvailableUsername(username));
    }

    @GetMapping("/validate/email")  // 이메일 중복확인
    @Operation(summary = "유저 email 사용가능 여부 확인 API")
    public ResponseEntity<Response> validateEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok().body(userService.isAvailableEmail(email));
    }

}
