package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseMessage;
import com.example.board.dto.ResponseMessage;
import com.example.board.entity.Board;
import com.example.board.security.SessionUtil;
import com.example.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Tag(name = "Board Controller", description = "Board Controller desc")
public class BoardController {

  @Autowired
  private BoardService boardService;

  @Autowired
  private SessionUtil sessionUtil;

  @GetMapping("/board/write") //localhost:8080/board/write
  public String writeBoardPage(HttpServletRequest request) throws Exception {
    try {
      sessionUtil.checkSession(request);
    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:/home";
    }
    return "boardpost";
  }

  @GetMapping("/board/list")
  public String boardListPage(@RequestParam(defaultValue = "0") Integer pageNumber,
      @RequestParam(required = false) String title, @RequestParam(defaultValue = "10") Integer size,
      Model model) {
    try {
      Pageable pageable = PageRequest.of(pageNumber, size);
      Page<Board> boardlist = boardService.selectTitleContainingBoard(title, pageable);
      model.addAttribute("list", boardlist);
    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:/home";
    }
    return "boardlist";
  }

  @GetMapping("/board/detail/{boardId}")
  public String boardDetail(@PathVariable Integer boardId, Model model) throws Exception {
    try {
      BoardDto boardDto = boardService.getBoardDetail(boardId);
      model.addAttribute("boardDetail", boardDto);
    } catch (Exception e) {
      return "redirect:/home";
    }
    return "boarddetail";
  }

  @PostMapping("/api/board/writeboard")
  @Operation(summary = "게시물 작성", description = "게시물 작성 API")
  public ResponseEntity<?> writeBoard(@RequestBody BoardRequestDto boardVal,
      HttpServletRequest request) {
    try {
      boolean check = sessionUtil.checkSession(request);
      BoardDto board = new BoardDto(boardVal.getTitle(), boardVal.getContent(), 3);
      boardService.boardPost(board);

      return ResponseEntity.status(HttpStatus.OK)
          .body(new BoardResponseMessage<>("게시물 작성 성공", board, true));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ResponseMessage("게시물 작성 오류", false));
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
  public ResponseEntity<?> boardDetailSearch(@PathVariable("boardId") int boardId) {
    try {
      BoardDto boardDto = boardService.getBoardDetail(boardId);

      return ResponseEntity.status(HttpStatus.OK)
          .body(new BoardResponseMessage<>("게시판 세부정보 조회 성공", boardDto, true));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ResponseMessage("게시물 조회 에러", false));
    }
  }

  @PutMapping("/api/board/update")
  @Operation(summary = "게시물 수정", description = "게시물 수정 API")
  public ResponseEntity<?> updateBoard(Integer id, BoardRequestDto boardRequestDto,
      HttpServletRequest request) {
    try {
      sessionUtil.checkSession(request);
      boardService.updateBoard(id, boardRequestDto);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      System.out.println("Error : 게시물 수정에 실패했습니다.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @DeleteMapping("/api/board/delete/{boardId}")
  @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
  public ResponseEntity<?> deleteBoard(@PathVariable("boardId") int boardId,
      HttpServletRequest request) {
    try {
      sessionUtil.checkSession(request);
      boardService.deleteBoard(boardId);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
