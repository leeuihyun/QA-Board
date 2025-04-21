package com.example.board.service;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import java.util.List;

public interface BoardService {

  void boardPost(BoardDto boardDto);

  BoardDto getBoardDetail(Integer boardId) throws Exception;

  List<Board> boardList();

  Integer updateBoard(Integer id, BoardRequestDto requestDto) throws Exception;

  void deleteBoard(Integer boardId) throws Exception;
}
