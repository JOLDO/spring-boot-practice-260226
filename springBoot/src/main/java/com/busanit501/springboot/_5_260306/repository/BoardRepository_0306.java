package com.busanit501.springboot._5_260306.repository;

import com.busanit501.springboot._5_260306.domain.Board_0306;
import com.busanit501.springboot._5_260306.repository.search.BoardSearch_0306;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository_0306 extends JpaRepository<Board_0306, Long>, BoardSearch_0306 {   //<Board엔티티, pk타입>
    //아무 기능이 없지만 상속받은 기본기능을 이용해서 CRUD를 할수 있음

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board_0306 b where b.bno = :bno")
    Optional<Board_0306> findByIdWithImages(Long bno);
}
