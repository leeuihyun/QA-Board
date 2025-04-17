package com.example.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Board extends BaseTimeEntity {

  @Id // 프라이머리키를 의미
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private String content;
  private Integer memberid;
  private Integer viewcnt;
  //private LocalDateTime created_at;
  //private LocalDateTime updated_at;

  public Board(String title, String content, int memberid, int viewcnt) {
    this.title = title;
    this.content = content;
    this.memberid = memberid;
    this.viewcnt = viewcnt;
  }

  public void addViewCnt() {
    this.viewcnt++;
  }

  public void updateBoard(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
