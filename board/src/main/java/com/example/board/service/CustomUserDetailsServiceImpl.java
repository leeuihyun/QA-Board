package com.example.board.service;

import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import com.example.board.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByUseremail(email);

    if (user == null) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }

    return new CustomUserDetails(user);
  }
}
