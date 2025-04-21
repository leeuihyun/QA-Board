package com.example.board.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public LoginFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected String obtainUsername(HttpServletRequest request) {
    return request.getParameter("useremail");
  }

  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> jsonMap = mapper.readValue(request.getInputStream(), Map.class);

    String useremail = jsonMap.get("useremail");
    String password = jsonMap.get("password");

    System.out.println(useremail);

    //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        useremail, password, null);

    //token에 담은 검증을 위한 AuthenticationManager로 전달
    return authenticationManager.authenticate(authToken);
  }

  //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) {
    System.out.println("성공 ");
  }

  //로그인 실패시 실행하는 메소드
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) {
    System.out.println("에러 ");
  }
}
