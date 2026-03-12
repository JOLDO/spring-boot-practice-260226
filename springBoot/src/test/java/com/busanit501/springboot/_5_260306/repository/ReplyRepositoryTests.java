package com.busanit501.springboot._5_260306.repository;

import com.busanit501.springboot._5_260306.domain.Board_0306;
import com.busanit501.springboot._5_260306.domain.Reply_0306;
import com.busanit501.springboot._5_260306.dto.BoardListReplyCountDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository_0306 replyRepository;

    @Autowired
    private BoardRepository_0306 boardRepository;

    @Test
    public void testInsert() {
        //각자 데이터베이스의 게시글 하나에 더미 댓글 작성
        Long bno = 19L;
        Board_0306 board = Board_0306.builder()
            .bno(bno)
            .build();

        Reply_0306 reply = Reply_0306.builder()
            .board(board)
            .replyText("ㅇㅋ")
            .replyer("졸도")
            .build();

        replyRepository.save(reply);
    }

    @Test
    public void testBoardReplies() {
        Long bno = 132L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply_0306> result = replyRepository.listOfBoard(bno, pageable);
        result.getContent().forEach(reply -> {
            log.info("댓글 테스트 조회 페이징 처리 : " + reply);
        });
    }

    @Test
    public void testSelectWithReplyCount() {
        Pageable pageable = PageRequest.of(8, 10,
                Sort.by("bno").descending());
        // 전달할 준비물
        // 1) 검색어, 2) 검색 유형
        String keyword = "더미";
        String[] types = {"t", "w", "c"};

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        log.info("result.getTotalElements()전체 갯수 :" + result.getTotalElements());
        log.info("result.getTotalPages()총페이지등 :" + result.getTotalPages());
        log.info("result.getContent() 페이징된 결과물 10개 :" + result.getContent());
        log.info("result.getNumber() 현재 페이지 번호 :" + result.getNumber());
        log.info("result.getSize() 크기  :" + result.getSize());
        log.info("result.hasNext() 다음  :" + result.hasNext());
        log.info("result.hasPrevious() 이전  :" + result.hasPrevious());
    }
}
