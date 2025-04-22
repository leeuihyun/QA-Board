// JWT TOKEN
/*
package com.example.board.service;

import com.example.board.dto.CustomUserDetails;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
    User user = userRepository.findByUseremail(useremail);

    if (user != null) {
      return new CustomUserDetails(user);
    }

    return null;
  }
}
*/