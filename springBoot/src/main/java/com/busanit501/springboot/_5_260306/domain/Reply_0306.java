package com.busanit501.springboot._5_260306.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//테이블에 테이블 이름 + 인덱스 설정
//인덱스 이름의 명명법은 idx_테이블명_컬럼명
//columnList = "board_bno" : 어떤 컬럼(reply테이블의 board_bno컬럼)을 기준으로 목록을 만들지
@Table(name="reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")  //manytoone으로 인해 만들어진 컬럼명을 이용해 인덱스 추가
})
@ToString(exclude = "board")
//댓글 테이블 내용만 조회, board테이블(부모테이블)의 데이터는 제외
//reply 테이블을 출력하면 board가 reply테이블에 있기 때문에 board 엔티티의 toString도 같이 실행되는걸 막기 위해서
public class Reply_0306 extends BaseEntity_0306{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;   //댓글번호

    @ManyToOne(fetch = FetchType.LAZY)  //필요할때(늦게) 조회할 예정
    //테이블 관계상 댓글 테이블에서 게시글의 아이디를 가지고 있어야 함(fk) 그래서 여기 기준으로 다수가 게시물 하나를 보기때문에 manytoone
    //LAZY: board 데이터를 즉시 JOIN하지 않고 실제로 접근할 때만 조회 (EAGER는 항상 즉시 JOIN)
    //manytoone을 사용해서 fk로 board(변수명)_bno(pk컬럼명)으로 지정해서 만들어줌
    private Board_0306 board;  //부모 게시글

    private String replyText;   //댓글 내용
    private String replyer; //댓글 작성자

    public void changeText(String text) {
        this.replyText = text;
    }

    public void changeBoard(Board_0306 board) {
        this.board = board;
    }
}
