package com.example.board.service;

import com.example.board.dto.LogInDto;
import com.example.board.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

public interface UserService {

  void signUp(UserDto userDto) throws Exception;

  void logIn(
      LogInDto logInDto,
      HttpServletRequest request) throws Exception;

  void logOut(HttpServletRequest request, HttpServletResponse response) throws SecurityException;

  void deleteUser(Integer userId, HttpServletRequest request, HttpServletResponse response)
      throws Exception;
}
