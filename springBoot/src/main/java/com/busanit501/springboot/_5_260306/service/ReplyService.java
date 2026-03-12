package com.busanit501.springboot._5_260306.service;

import com.busanit501.springboot._5_260306.dto.PageRequestDTO_0306;
import com.busanit501.springboot._5_260306.dto.PageResponseDTO_0306;
import com.busanit501.springboot._5_260306.dto.ReplyDTO_0306;

public interface ReplyService {
    Long register(ReplyDTO_0306 replyDTO);

    ReplyDTO_0306 read(Long rno);

    void modify(ReplyDTO_0306 replyDTO);

    void remove(Long rno);

    //게시글 하나에 대한 댓글 목록
    PageResponseDTO_0306<ReplyDTO_0306> getListOfBoard(Long bno, PageRequestDTO_0306 pageRequestDTO);
}
