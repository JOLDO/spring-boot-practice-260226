package com.busanit501.springboot._5_260306.repository;

import com.busanit501.springboot._5_260306.domain.Board_0306;
import com.busanit501.springboot._5_260306.domain.Reply_0306;
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

    @Test
    public void testInsert() {
        //각자 데이터베이스의 게시글 하나에 더미 댓글 작성
        Long bno = 129L;
        Board_0306 board = Board_0306.builder()
            .bno(bno)
            .build();

        Reply_0306 reply = Reply_0306.builder()
            .board(board)
            .replyText("샘플 댓글3")
            .replyer("샘플 작성자2")
            .build();

        replyRepository.save(reply);
    }

    @Test
    public void testBoardReplies() {
        Long bno = 129L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply_0306> result = replyRepository.listOfBoard(bno, pageable);
        result.getContent().forEach(reply -> {
            log.info("댓글 테스트 조회 페이징 처리 : " + reply);
        });
    }
}
