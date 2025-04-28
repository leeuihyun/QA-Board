package com.example.board.security;

import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

  private final SessionStorage sessionStorage;
  private final UserRepository userRepository;

  public SessionUtil(SessionStorage sessionStorage, UserRepository userRepository) {
    this.sessionStorage = sessionStorage;
    this.userRepository = userRepository;
  }

  public boolean checkSession(HttpServletRequest req) throws SecurityException {
    String sessionId = Arrays.stream(req.getCookies())
        .filter(cookie -> cookie.getName().equals("JSESSIONID"))
        .map(Cookie::getValue)
        .findAny()
        .orElseThrow(() -> new SecurityException("세션 정보 없음"));

    if (sessionStorage.doesNotContains(sessionId)) {
      throw new SecurityException("포함하지 않는 세션입니다.");
    }

    int id = Integer.parseInt(sessionStorage.getIdBySessionId(sessionId));
    Optional<User> user = userRepository.findById(id);

    if (!user.isPresent()) {
      throw new SecurityException("유저 이메일이 올바르지 않은 세션입니다.");
    }

    return true;
  }
}
