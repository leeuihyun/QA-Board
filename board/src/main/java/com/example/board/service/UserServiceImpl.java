package com.example.board.service;

import com.example.board.dto.UserDto;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void signUp(UserDto userDto) throws Exception {

    boolean isExist = userRepository.existsByUseremail(userDto.getUseremail());

    if (isExist) {
      throw new Exception("회원가입 에러가 발생했습니다.");
    }

    User user = new User(userDto.getUseremail(), userDto.getUsername(),
        passwordEncoder.encode(userDto.getPassword()), "ROLE_USER");

    userRepository.save(user);
  }
}
