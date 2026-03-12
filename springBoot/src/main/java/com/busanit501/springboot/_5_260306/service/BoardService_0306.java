package com.busanit501.springboot._5_260306.service;

import com.busanit501.springboot._5_260306.dto.BoardDTO_0306;
import com.busanit501.springboot._5_260306.dto.BoardListReplyCountDTO;
import com.busanit501.springboot._5_260306.dto.PageRequestDTO_0306;
import com.busanit501.springboot._5_260306.dto.PageResponseDTO_0306;

public interface BoardService_0306 {
    Long register(BoardDTO_0306 boardDTO_0306);

    BoardDTO_0306 readOne(Long bno);

    void modify(BoardDTO_0306 boardDTO);

    void remove(Long bno);

    PageResponseDTO_0306<BoardDTO_0306> list(PageRequestDTO_0306 pageRequestDTO);

    //전체 목록 + 댓글갯수
    PageResponseDTO_0306<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO_0306 pageRequestDTO);
}
