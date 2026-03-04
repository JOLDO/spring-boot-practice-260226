package com.busanit501.springboot._3_260304.service;

import com.busanit501.springboot._3_260304.dto.BoardDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageRequestDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageResponseDTO_0304;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
    @Autowired private BoardService_0304 boardService_0304;

    @Test
    public void testRegister() {
        BoardDTO_0304 boardDTO = BoardDTO_0304.builder()
            .title("허허")
            .content("낄낄")
            .writer("졸도")
            .build();
        Long bno = boardService_0304.register(boardDTO);
        log.info("출력 등록된 bno 확인 : " + bno);
    }

    @Test
    public void testSelectOne() {
        Long bno = 101L;
        BoardDTO_0304 boardDTO = boardService_0304.readOne(bno);
        log.info("하나조회 결과 : " + boardDTO);
    }

    @Test
    public void testModify() {
        //변경할 내용을 일단 DB에서 불러오고 내용을 변경해서 전달
        //101번 번호의 엔티티 객체를 이용해도 됨
        BoardDTO_0304 boardDTO = boardService_0304.readOne(101L);
        boardDTO.setTitle("낄낄낄");
        boardDTO.setContent("깔깔깔");
        boardService_0304.modify(boardDTO);
    }

    @Test
    public void testRemove() {
        boardService_0304.remove(101L);
    }

    @Test
    public void testList() {
        //준비물 :  화면에서 전달받은 페이징 정보와 검색정보를 담은 PageRequestDTO_0304 필요함
        PageRequestDTO_0304 pageRequestDTO = PageRequestDTO_0304.builder()
            .type("tcw")
            .keyword("더미")
            .page(1)
            .size(10)
            .build();
        PageResponseDTO_0304<BoardDTO_0304> responseDTO = boardService_0304.list(pageRequestDTO);
        log.info("출력 responseDTO 확인 : " + responseDTO);
    }
}
