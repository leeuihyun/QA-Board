package com.example.board.repository;

import com.example.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

  // 전체 조회
  Page<Board> findAll(Pageable pageable);
  
  // 타이틀 포함 전체 조회
  Page<Board> findAllByTitleContaining(String title, Pageable pageable);
}
