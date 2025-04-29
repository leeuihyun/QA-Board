package com.example.board.controller;

import com.example.board.dto.LogInDto;
import com.example.board.dto.ResponseMessage;
import com.example.board.dto.UserDto;
import com.example.board.dto.UserIdDto;
import com.example.board.security.SessionUtil;
import com.example.board.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Controller desc")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private SessionUtil sessionUtil;

  @GetMapping("/home")
  public String homePage() {
    return "home";
  }

  @GetMapping("/signup")
  public String signUpPage(HttpServletRequest request) {
    try {
      sessionUtil.checkSession(request);
    } catch (Exception e) {
      return "signup";
    }
    return "redirect:/home";
  }

  @GetMapping("/login")
  public String logInPage(HttpServletRequest request) {
    try {
      sessionUtil.checkSession(request);
    } catch (Exception e) {
      return "login";
    }
    return "redirect:/home";

  }

  @GetMapping("/logout")
  public String logOutPage(HttpServletRequest request) {
    try {
      sessionUtil.checkSession(request);
    } catch (Exception e) {
      return "redirect:/home";
    }
    return "logout";

  }

  @PostMapping("/signup")
  @Operation(summary = "회원가입", description = "회원가입 API")
  public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
    try {
      userService.signUp(userDto);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ResponseMessage("회원가입 에러", false));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseMessage("회원가입 성공", true));
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
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ResponseMessage("로그인 에러", false));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseMessage("로그인 성공", true));
  }

  @PostMapping("logout")
  @Operation(summary = "로그아웃", description = "로그아웃 API")
  public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response) {
    try {
      sessionUtil.checkSession(request);
      userService.logOut(request, response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ResponseMessage("로그아웃 에러", false));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseMessage("로그아웃 성공", true));
  }

  @PostMapping("/delete/user")
  @Operation(summary = "회원 계정 삭제", description = "회원 계정 삭제 API")
  public ResponseEntity<?> delete(@RequestBody UserIdDto userIdDto, HttpServletRequest request,
      HttpServletResponse response) {
    try {
      sessionUtil.checkSession(request);
      userService.deleteUser(userIdDto.getUserId(), request, response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ResponseMessage("계정 삭제 실패", false));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseMessage("계정 삭제 성공", true));
  }
}
