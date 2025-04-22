package com.example.board.service;

import com.example.board.dto.UserDto;

public interface UserService {

  void signUp(UserDto userDto) throws Exception;
}
