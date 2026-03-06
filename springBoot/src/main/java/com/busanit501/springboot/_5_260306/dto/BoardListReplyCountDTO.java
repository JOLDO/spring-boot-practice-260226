package com.busanit501.springboot._5_260306.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {
    //목록화면에 보이는 컬럼의 목록 + 댓글의 갯수
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;

    private Long replyCount;
}
