package com.busanit501.springboot._3_260304.service;

import com.busanit501.springboot._3_260304.dto.BoardDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageRequestDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageResponseDTO_0304;

public interface BoardService_0304 {
    Long register(BoardDTO_0304 boardDTO_0304);

    BoardDTO_0304 readOne(Long bno);

    void modify(BoardDTO_0304 boardDTO);

    void remove(Long dno);

    PageResponseDTO_0304<BoardDTO_0304> list(PageRequestDTO_0304 pageRequestDTO);
}
