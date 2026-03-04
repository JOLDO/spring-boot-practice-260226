package com.busanit501.springboot._3_260304.repository.search;

import com.busanit501.springboot._3_260304.domain.Board_0304;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch_0304 {
    //검색의 결과도 페이징 처리를 할 예정
    Page<Board_0304>  search1(Pageable pageable);

    //검색어(제목/내용), 페이징 처리 적용
    Page<Board_0304> searchAll(String[] types, String keyword, Pageable pageable);
}
