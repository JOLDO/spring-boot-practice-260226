package com.busanit501.springboot._2_260303.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="board")
//추가기능 : 어노테이션 옵션 추가
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board_0303 extends BaseEntity_0303 {  //베이스 엔티티를 상속 받으면 됨
    //엔티티 클래스는 실제 데이터베이스의 테이블을 만드는 효과로, pk가 반드시 있어야함
    //필수로 @Id로 pk표시를 의무적으로 해야함
    @Id
    //마리아 db에서 사용하는 기본 자동 생성 정책을 이용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;   //실제 db의 데이블 컬럼1

    @Column(length = 500, nullable = false) //500자까지, notnull
    private String title;   //실제 db의 데이블 컬럼2

    @Column(length = 2000, nullable = false)
    private String content;   //실제 db의 데이블 컬럼3

    @Column(length = 50, nullable = false)
    private String writer;   //실제 db의 데이블 컬럼4

    //상속을 받았기 때문에 regDate와 modDate가 추가될 예정
    // 수정 메서드를 따로 만들기.
    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
