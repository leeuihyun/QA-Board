package com.example.board.controller;

import com.example.board.dto.LogInDto;
import com.example.board.dto.UserDto;
import com.example.board.dto.UserIdDto;
import com.example.board.security.SessionUtil;
import com.example.board.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Controller desc")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private SessionUtil sessionUtil;

  @GetMapping("/home")
  public String homePage(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    System.out.println("▶ 기존 세션: " + session);

    HttpSession forceSession = request.getSession();
    System.out.println("▶ 강제로 만든 세션: " + forceSession.getId());
    return "home";
  }

  @GetMapping("/signup")
  public String signUpPage() {
    return "signup";
  }

  @GetMapping("/login")
  public String logInPage() {
    return "login";
  }

  @GetMapping("/logout")
  public String logOutPage() {
    return "logout";
  }

  @PostMapping("/signup")
  @Operation(summary = "회원가입", description = "회원가입 API")
  public ResponseEntity<?> signUp(@RequestBody UserDto userDto) throws Exception {
    try {
      userService.signUp(userDto);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    return ResponseEntity.status(HttpStatus.OK).body(userDto);
  }

  @PostMapping("/login")
  @Operation(summary = "로그인", description = "로그인 API")
  public ResponseEntity<?> logIn(
      @RequestBody LogInDto logInDto,
      HttpServletRequest request) throws Exception {
    try {
      userService.logIn(logInDto, request);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("로그인 에러");
    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("logout")
  @Operation(summary = "로그아웃", description = "로그아웃 API")
  public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    try {
      boolean sessionIsExist = sessionUtil.checkSession(request);
      userService.logOut(request, response);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/delete/user")
  @Operation(summary = "회원 계정 삭제", description = "회원 계정 삭제 API")
  public ResponseEntity<?> delete(@RequestBody UserIdDto userIdDto, HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    try {
      boolean sessionIsExist = sessionUtil.checkSession(request);
      userService.deleteUser(userIdDto.getUserId(), request, response);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
