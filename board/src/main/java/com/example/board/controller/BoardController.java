package com.example.board.controller;

import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public String boardWritePost(BoardRequestDto boardVal) {
    System.out.println("제목 : " + boardVal.getTitle());
    System.out.println("내용 : " + boardVal.getContent());

    Board board = new Board(boardVal.getTitle(), boardVal.getContent(), 1, 0);
    boardService.boardPost(board);

    return "redirect:/boardpost";
  }

  @GetMapping("/board/list")
  public String boardList(Model model) {
    List<Board> list = boardService.boardList();
    model.addAttribute("list", list);
    return "boardlist";
  }
}
