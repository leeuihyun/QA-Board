package com.example.board.controller;

import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

  @Autowired
  private BoardService boardService;

  @GetMapping("/board/write") //localhost:8080/board/write
  public String boardWriteForm() {
    return "boardpost";
  }

  @PostMapping("/board/writepost")
  public String boardWritePost(Board board) {
    System.out.println("제목 : " + board.getTitle());
    System.out.println("내용 : " + board.getContent());

    boardService.boardPost(board);

    return "redirect:/boardpost";
  }
}
