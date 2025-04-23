package com.example.board.service;

import com.example.board.security.Encoder;
import com.example.board.dto.LogInDto;
import com.example.board.dto.UserDto;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import com.example.board.security.SessionStorage;
import com.example.board.security.SessionUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private SessionStorage sessionStorage;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionUtil sessionUtil;

  @Autowired
  private Encoder encoder;

  @Override
  public void signUp(UserDto userDto) throws Exception {

    boolean isExist = userRepository.existsByUseremail(userDto.getUseremail());

    if (isExist) {
      throw new Exception("회원가입 에러가 발생했습니다.");
    }

    User user = new User(userDto.getUseremail(), userDto.getUsername(),
        userDto.getPassword(), "ROLE_USER");

    userRepository.save(user);
  }

  @Override
  public void logIn(
      LogInDto logInDto,
      HttpServletRequest request) throws Exception {

    boolean isExist = userRepository.existsByUseremail(logInDto.getUseremail());
    User user = userRepository.findByUseremail(logInDto.getUseremail());

    if (!isExist) {
      throw new NullPointerException("가입되지 않은 유저입니다.");
    }

    if (!logInDto.getPassword().equals(user.getPassword())) {
      throw new Exception("올바르지 않은 비밀번호입니다.");
    }

    HttpSession session = request.getSession(true);
    session.setAttribute("email", user.getUseremail());
    session.setMaxInactiveInterval(3600);
    String sessionId = session.getId();
    sessionStorage.addSession(sessionId, session);
  }

  @Override
  public void logOut(HttpServletRequest request, HttpServletResponse response)
      throws SecurityException {
    HttpSession session = request.getSession(false);

    if (session == null) {
      throw new SecurityException("세션이 존재하지 않습니다.");
    }

    String sessionId = session.getId();
    sessionStorage.deleteSession(sessionId);
    session.invalidate();

    Cookie cookie = new Cookie("JSESSIONID", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);

  }

  @Override
  public void deleteUser(Integer userId, HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    HttpSession session = request.getSession(false);

    if (session == null) {
      throw new SecurityException("세션이 존재하지 않습니다.");
    }

    String sessionId = session.getId();
    sessionStorage.deleteSession(sessionId);
    session.invalidate();

    Cookie cookie = new Cookie("JSESSIONID", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);

    Optional<User> user = userRepository.findById(userId);

    if (user.isPresent()) {
      userRepository.delete(user.get());
    } else {
      throw new Exception("유저가 존재하지 않습니다.");
    }
  }
}
