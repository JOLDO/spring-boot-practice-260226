package com.busanit501.springboot._3_260304.service;

import com.busanit501.springboot._3_260304.dto.BoardDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageRequestDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageResponseDTO_0304;
import com.busanit501.springboot._5_260306.dto.PageRequestDTO_0306;
import com.busanit501.springboot._5_260306.dto.PageResponseDTO_0306;
import com.busanit501.springboot._5_260306.dto.ReplyDTO_0306;
import com.busanit501.springboot._5_260306.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testReplyRegister() {
        ReplyDTO_0306 replyDTO = ReplyDTO_0306.builder()
            .replyText("샘플 댓글 작성 서비스 테스트 내용")
            .replyer("샘플 사용자")
            .bno(133L)
            .build();
        log.info("출력 작성 후 댓글 번호 : " + replyService.register(replyDTO));
    }

    @Test
    public void testReplySelectOne() {
        long rno = 10L;
        ReplyDTO_0306 replyDTO = replyService.read(rno);
        log.info("출력 댓글 서비스 단위 테스트 중, 댓글 조회 : " + replyDTO);
    }

    @Test
    public void testReplyUpdate() {
        long rno = 10L;
        ReplyDTO_0306 replyDTO = replyService.read(rno);
        replyDTO.setReplyText("수정수정!!!");
        replyService.modify(replyDTO);
        log.info("댓글 서비스 단위 테스트 중, 댓글 수정");
    }

    @Test
    public void testReplyDelete() {
        replyService.remove(10L);
    }

    @Test
    public void testReplySelectList() {
        //준비물 :  화면에서 전달받은 페이징 정보와 검색정보를 담은 PageRequestDTO_0304 필요함
        PageRequestDTO_0306 pageRequestDTO = PageRequestDTO_0306.builder()
                .type("tcw")
                .keyword("샘플")
                .page(1)
                .size(10)
                .build();
        PageResponseDTO_0306<ReplyDTO_0306> responseDTO = replyService.getListOfBoard(133L, pageRequestDTO);
        log.info("출력 responseDTO 확인 : " + responseDTO);
    }
}
