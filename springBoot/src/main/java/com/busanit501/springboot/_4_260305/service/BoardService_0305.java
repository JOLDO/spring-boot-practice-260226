package com.busanit501.springboot._4_260305.service;

import com.busanit501.springboot._4_260305.dto.BoardDTO_0305;
import com.busanit501.springboot._4_260305.dto.PageRequestDTO_0305;
import com.busanit501.springboot._4_260305.dto.PageResponseDTO_0305;

public interface BoardService_0305 {
    Long register(BoardDTO_0305 boardDTO_0305);

    BoardDTO_0305 readOne(Long bno);

    void modify(BoardDTO_0305 boardDTO);

    void remove(Long bno);

    PageResponseDTO_0305<BoardDTO_0305> list(PageRequestDTO_0305 pageRequestDTO);
}
