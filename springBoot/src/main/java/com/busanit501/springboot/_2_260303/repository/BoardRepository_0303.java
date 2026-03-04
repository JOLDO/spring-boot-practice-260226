package com.busanit501.springboot._2_260303.repository;

import com.busanit501.springboot._2_260303.domain.Board_0303;
import com.busanit501.springboot._2_260303.repository.search.BoardSearch_0303;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository_0303 extends JpaRepository<Board_0303, Long>, BoardSearch_0303 {   //<Board엔티티, pk타입>
    //아무 기능이 없지만 상속받은 기본기능을 이용해서 CRUD를 할수 있음

}
