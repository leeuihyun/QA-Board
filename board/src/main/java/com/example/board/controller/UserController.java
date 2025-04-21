package com.example.board.controller;

import com.example.board.dto.UserDto;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  public String signUp(@RequestBody UserDto userDto) {
    userService.signUp(userDto);

    return "ok";
  }
}
