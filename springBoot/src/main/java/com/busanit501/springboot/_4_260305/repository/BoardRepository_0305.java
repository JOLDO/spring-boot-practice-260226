package com.busanit501.springboot._4_260305.repository;

import com.busanit501.springboot._4_260305.domain.Board_0305;
import com.busanit501.springboot._4_260305.repository.search.BoardSearch_0305;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository_0305 extends JpaRepository<Board_0305, Long>, BoardSearch_0305 {   //<Board엔티티, pk타입>
    //아무 기능이 없지만 상속받은 기본기능을 이용해서 CRUD를 할수 있음

}
