package com.busanit501.springboot._2_260303.repository.search;

import com.busanit501.springboot._2_260303.domain.Board_0303;
import com.busanit501.springboot._2_260303.domain.QBoard_0303;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearch_0303Impl extends QuerydslRepositorySupport implements BoardSearch_0303 {

    public BoardSearch_0303Impl() { //생성자 만들라는 버튼 클릭 + 매게변수 삭제 + super(사용할 엔티티.class)
        super(Board_0303.class);
    }

    @Override
    public Page<Board_0303> search1(Pageable pageable) {    //메서드 구현하라는 버튼 클릭
        //자바문법으로 검색 및 필터에 필요한 문장을 작성
        QBoard_0303 board = QBoard_0303.board_0303; //Q도메인 객체를 이용
        JPQLQuery<Board_0303> query = from(board);  //select * from board;

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

        List<Board_0303> list  = query.fetch(); //DB서버로부터 호출해서 데이터를 받아옴
        long count = query.fetchCount(); //조회된 데이터 갯수 확인
        return null;
    }

    //String[] types : "t", "c", "w"
    @Override
    public Page<Board_0303> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard_0303 board = QBoard_0303.board_0303;
        JPQLQuery<Board_0303> query = from(board);

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
        List<Board_0303> list = query.fetch();  //페이징 처리가 된 10개의 데이터 목록

        //전체 갯수
        long total = query.fetchCount();

        //페이지 타입으로 전달
        Page<Board_0303> result = new PageImpl<Board_0303>(list, pageable, total);

        return result;
    }
}
