package com.busanit501.springboot._5_260306.repository.search;

import com.busanit501.springboot._4_260305.domain.QBoard_0305;
import com.busanit501.springboot._5_260306.domain.Board_0306;
import com.busanit501.springboot._5_260306.domain.QBoard_0306;
import com.busanit501.springboot._5_260306.domain.QReply_0306;
import com.busanit501.springboot._5_260306.dto.BoardListReplyCountDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardSearch_0306Impl extends QuerydslRepositorySupport implements BoardSearch_0306 {

    public BoardSearch_0306Impl() { //생성자 만들라는 버튼 클릭 + 매게변수 삭제 + super(사용할 엔티티.class)
        super(Board_0306.class);
    }

    @Override
    public Page<Board_0306> search1(Pageable pageable) {    //메서드 구현하라는 버튼 클릭
        //자바문법으로 검색 및 필터에 필요한 문장을 작성
        QBoard_0306 board = QBoard_0306.board_0306; //Q도메인 객체를 이용
        JPQLQuery<Board_0306> query = from(board);  //select * from board;

        query.where(board.title.contains("t"));  //where title like ~

        //제목, 내용 검색 조건 처리
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(board.title.contains("t"));
        booleanBuilder.or(board.content.contains("t"));

        //쿼리 조전 적용
        query.where(booleanBuilder);

        //유효성 체크 : bno > 0
        query.where(board.bno.gt(0L));

        //페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board_0306> list  = query.fetch(); //DB서버로부터 호출해서 데이터를 받아옴
        long count = query.fetchCount(); //조회된 데이터 갯수 확인
        return null;
    }

    //String[] types : "t", "c", "w"
    @Override
    public Page<Board_0306> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard_0306 board = QBoard_0306.board_0306;
        JPQLQuery<Board_0306> query = from(board);

        //검색 조건 사용
        if(types != null && types.length > 0 && keyword != null) {
            //여러 조건을 BooleanBuilder를 이용해서 조건의 묶음을 만듦
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types) {
                switch (type) {
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;

                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                        break;

                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            //조건부 설정 적용
            query.where(booleanBuilder);
        }

        //간단한 유효성 체크 : bno > 0
        query.where(board.bno.gt(0L));

        //페이징 조건 적용
        this.getQuerydsl().applyPagination(pageable, query);

        //위의 준비물을 이용해서 검색이나 필터, 페이징의 결과를 반환
        List<Board_0306> list = query.fetch();  //페이징 처리가 된 10개의 데이터 목록

        //전체 갯수
        long total = query.fetchCount();

        //페이지 타입으로 전달
        Page<Board_0306> result = new PageImpl<Board_0306>(list, pageable, total);

        return result;
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        QBoard_0306 board = QBoard_0306.board_0306;
        QReply_0306 reply = QReply_0306.reply_0306;

        JPQLQuery<Board_0306> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));    //board테이블의 bno와 reply테이블의 board_bno가 같은걸 레프트(아우터) 조인함
        query.groupBy(board);

        //검색 조건 사용
        if(types != null && types.length > 0 && keyword != null) {
            //여러 조건을 BooleanBuilder를 이용해서 조건의 묶음을 만듦
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types) {
                switch (type) {
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;

                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                        break;

                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            //조건부 설정 적용
            query.where(booleanBuilder);
        }

        //간단한 유효성 체크 : bno > 0
        query.where(board.bno.gt(0L));

        //자동으로 entity의 결과를 dto로 변환하는 작업
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean(BoardListReplyCountDTO.class,
                board.bno,
                board.title,
                board.writer,
                board.regDate,
                reply.count().as("replyCount")
        ));

        //페이징 조건 이용
        //페이징 처리
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        //준비물 1)페이징 2)검색 3)목록의 댓글
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();  //페이징 처리가 된 10개의 데이터 목록

        //전체 갯수
        long total = dtoQuery.fetchCount();

        //페이지 타입으로 전달
        Page<BoardListReplyCountDTO> result = new PageImpl<BoardListReplyCountDTO>(dtoList, pageable, total);

        return result;
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {
        QBoard_0306 board = QBoard_0306.board_0306;
        QReply_0306 reply = QReply_0306.reply_0306;

        JPQLQuery<Board_0306> boardJPQLQuery = from(board);
        boardJPQLQuery.leftJoin(reply).on(reply.board.eq(board));    //board테이블의 bno와 reply테이블의 board_bno가 같은걸 레프트(아우터) 조인함

        getQuerydsl().applyPagination(pageable, boardJPQLQuery);

        List<Board_0306> boardList = boardJPQLQuery.fetch();

        boardList.forEach(board1 -> {
            log.info("bno : " + board1.getBno());
            log.info("imageSet : " + board1.getImageSet());
            log.info("====================");
        });

        return null;
    }
}
