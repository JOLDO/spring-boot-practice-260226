package com.busanit501.springboot._5_260306.repository;

import com.busanit501.springboot._5_260306.domain.Reply_0306;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository_0306 extends JpaRepository<Reply_0306, Long> {
    @Query("select r from Reply_0306 r where r.board.bno = :bno")
    Page<Reply_0306> listOfBoard(Long bno, Pageable pageable);
}
