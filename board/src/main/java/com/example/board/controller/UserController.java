package com.example.board.controller;

import com.example.board.dto.UserDto;
import com.example.board.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Controller desc")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/home")
  public String homePage() {
    return "home";
  }

  @GetMapping("/signup")
  public String signUpPage() {
    return "signup";
  }

  @GetMapping("/login")
  public String logInPage(Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/home";
    }
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

}
