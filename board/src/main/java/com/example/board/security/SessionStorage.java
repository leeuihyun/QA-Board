package com.example.board.security;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SessionStorage {

  private final Map<String, HttpSession> sessions = new HashMap<>();

  public void addSession(String sessionId, HttpSession session) {
    sessions.put(sessionId, session);
  }

  public void deleteSession(String sessionId) {
    sessions.remove(sessionId);
  }

  public String getIdBySessionId(String sessionId) {
    return (String) sessions.get(sessionId).getAttribute("id");
  }

  public boolean doesNotContains(String sessionId) {
    return !sessions.containsKey(sessionId);
  }
}
