package com.example.board.service;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {

  @Autowired //의존성주입
  private BoardRepository boardRepository;

  public void boardPost(BoardDto board) {
    // 제목 중복 체크할 수 있을듯
    Board boardEntity = new Board(board.getTitle(), board.getContent(), board.getMemberid(), 0);
    boardRepository.save(boardEntity);
  }

  public List<Board> boardList() {
    return boardRepository.findAll();
  }

  @Transactional
  public BoardDto getBoardDetail(Integer boardId) throws Exception {
    Optional<Board> board = boardRepository.findById(boardId);

    if (board.isPresent()) {
      Board boardEntity = board.get();
      boardEntity.addViewCnt();

      return new BoardDto(boardEntity);
    } else {
      throw new Exception("search exception");
    }
  }

  @Transactional
  public Integer updateBoard(Integer id, BoardRequestDto requestDto) throws Exception {
    Optional<Board> boardEntity = boardRepository.findById(id);

    if (boardEntity.isPresent()) {
      Board board = boardEntity.get();
      board.updateBoard(requestDto.getTitle(), requestDto.getContent());
      return id;
    } else {
      throw new Exception("update exception");
    }
  }

  public void deleteBoard(Integer boardId) throws Exception {
    Optional<Board> board = boardRepository.findById(boardId);

    if (board.isPresent()) {
      boardRepository.delete(board.get());
    } else {
      throw new Exception("Id Null Exception");
    }
  }

  @Override
  public Page<Board> selectTitleContainingBoard(String title, Pageable pageable) throws Exception {
    try {
      if (title == null || title.trim().isEmpty()) {
        return boardRepository.findAll(pageable);
      }
      return boardRepository.findAllByTitleContaining(title, pageable);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
