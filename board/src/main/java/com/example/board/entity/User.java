package com.example.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String useremail;
  private String username;
  private String password;
  private String role;

  public User(String useremail, String username, String password, String role) {
    this.useremail = useremail;
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
