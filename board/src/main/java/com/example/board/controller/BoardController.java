package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "Board Controller", description = "Board Controller desc")
public class BoardController {

  @Autowired
  private BoardService boardService;

  @GetMapping("/board/write") //localhost:8080/board/write
  public String boardWriteForm() {
    return "boardpost";
  }

  @PostMapping("/board/writeboard")
  @Operation(summary = "게시물 작성", description = "게시물 작성 API")
  public String boardWritePost(@RequestBody BoardRequestDto boardVal) throws Exception {
    try {
      System.out.println("제목 : " + boardVal.getTitle());
      System.out.println("내용 : " + boardVal.getContent());

      BoardDto board = new BoardDto(boardVal.getTitle(), boardVal.getContent(), 1);

      boardService.boardPost(board);

      return "boardpost";
    } catch (Exception e) {
      System.out.println("Error : 게시물 수정에 실패했습니다.");
      return "boardpost";
    }
  }

  @GetMapping("/board/list")
  @Operation(summary = "게시물 리스트 조회", description = "게시물 리스트 조회 API")
  public String boardList(Model model) {
    List<Board> list = boardService.boardList();
    model.addAttribute("list", list);
    return "boardlist";
  }

  @GetMapping("/board/detail/{boardId}")
  @Operation(summary = "게시물 세부정보 조회", description = "게시물 세부정보 조회 API")
  public String boardDetailSearch(@PathVariable("boardId") int boardId) throws Exception {
    try {
      BoardDto boardDto = boardService.getBoardDetail(boardId);

      System.out.println(
          boardDto.getId() + " " + boardDto.getTitle() + " " + boardDto.getContent());
      return "boardlist";
    } catch (Exception e) {
      System.out.println("Error : 게시물 조회에 실패했습니다.");
      return "boardlist";
    }
  }

  @PutMapping("/board/update")
  @Operation(summary = "게시물 수정", description = "게시물 수정 API")
  public String updateBoard(Integer id, BoardRequestDto boardRequestDto) throws Exception {
    try {
      boardService.updateBoard(id, boardRequestDto);
      return "boardlist";
    } catch (Exception e) {
      System.out.println("Error : 게시물 수정에 실패했습니다.");
      return "boardlist";
    }
  }

  @DeleteMapping("/board/delete/{boardId}")
  @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
  public String deleteBoard(@PathVariable("boardId") int boardId) throws Exception {
    try {
      boardService.deleteBoard(boardId);
      return "boardlist";
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return "boardlist";
    }
  }
}
