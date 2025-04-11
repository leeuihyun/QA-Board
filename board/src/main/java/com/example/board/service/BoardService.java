package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  @Autowired //의존성주입
  private BoardRepository boardRepository;

  public void boardPost(Board board){
    boardRepository.save(board);
  }
}
