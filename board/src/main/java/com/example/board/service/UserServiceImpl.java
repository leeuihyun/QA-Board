package com.example.board.service;

import com.example.board.dto.UserDto;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public void signUp(UserDto userDto) {
    String useremail = userDto.getUseremail();
    String username = userDto.getUsername();
    String password = userDto.getPassword();

    Boolean isExist = userRepository.existsByUseremail(useremail);

    if (isExist) {
      return;
    }

    User user = new User(useremail, username, bCryptPasswordEncoder.encode(password), "ROLE_ADMIN");

    userRepository.save(user);
  }
}
