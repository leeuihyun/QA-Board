package com.example.board.dto;

import com.example.board.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
  private Integer id;
  private String title;
  private String content;
  private Integer memberid;
  private Integer viewcnt;

  public BoardDto(Board board) {
    this.id = board.getId();
    this.title = board.getTitle();
    this.content = board.getContent();
    this.memberid = board.getMemberid();
    this.viewcnt = board.getViewcnt();
  }

  public BoardDto(String title, String content, Integer memberid) {
    this.title = title;
    this.content = content;
    this.memberid = memberid;
  }
}
