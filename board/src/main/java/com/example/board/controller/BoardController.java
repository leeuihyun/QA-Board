package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.security.SessionUtil;
import com.example.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Tag(name = "Board Controller", description = "Board Controller desc")
public class BoardController {

  @Autowired
  private BoardService boardService;

  @Autowired
  private SessionUtil sessionUtil;

  @GetMapping("/board/write") //localhost:8080/board/write
  public String writeBoardPage() {
    return "boardpost";
  }

  @GetMapping("/board/list")
  public String boardListPage() {
    return "boardlist";
  }

  @PostMapping("/api/board/writeboard")
  @Operation(summary = "게시물 작성", description = "게시물 작성 API")
  public ResponseEntity<?> writeBoard(@RequestBody BoardRequestDto boardVal,
      HttpServletRequest request)
      throws Exception {
    try {
      boolean check = sessionUtil.checkSession(request);
      System.out.println(check);
      
      BoardDto board = new BoardDto(boardVal.getTitle(), boardVal.getContent(), 3);

      boardService.boardPost(board);

      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @GetMapping("/api/board/list")
  @Operation(summary = "게시물 리스트 조회", description = "게시물 리스트 조회 API")
  public ResponseEntity<List<Board>> boardList() {
    List<Board> list = boardService.boardList();
    return ResponseEntity.status(HttpStatus.OK).body(list);
  }

  @GetMapping("/api/board/detail/{boardId}")
  @Operation(summary = "게시물 세부정보 조회", description = "게시물 세부정보 조회 API")
  public ResponseEntity<BoardDto> boardDetailSearch(@PathVariable("boardId") int boardId)
      throws Exception {
    try {
      BoardDto boardDto = boardService.getBoardDetail(boardId);

      return ResponseEntity.status(HttpStatus.OK).body(boardDto);
    } catch (Exception e) {
      System.out.println("Error : 게시물 조회에 실패했습니다.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PutMapping("/api/board/update")
  @Operation(summary = "게시물 수정", description = "게시물 수정 API")
  public ResponseEntity<?> updateBoard(Integer id, BoardRequestDto boardRequestDto)
      throws Exception {
    try {
      boardService.updateBoard(id, boardRequestDto);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      System.out.println("Error : 게시물 수정에 실패했습니다.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @DeleteMapping("/api/board/delete/{boardId}")
  @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
  public ResponseEntity<?> deleteBoard(@PathVariable("boardId") int boardId) throws Exception {
    try {
      boardService.deleteBoard(boardId);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
