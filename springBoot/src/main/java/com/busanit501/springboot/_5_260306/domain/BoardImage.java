package com.busanit501.springboot._5_260306.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")    //BoardImage 조회시 Board 조회를 제외함
//Comparable : 비교해서 정렬을 하는 목
public class BoardImage implements Comparable<BoardImage> {
    @Id
    private String uuid;

    private String fileName;

    //하나의 게시글에 여러 첨부이미지가 있을 경우 순서를 정하는 번호
    private int ord;

    //단방향 설정
    @ManyToOne
    private Board_0306 board;

    @Override
    public int compareTo(BoardImage other) {
        //결과가 음수면 앞으로 배치 양수면 뒤로 배치
        return this.ord - other.ord;
    }

    //엔티티에서는 불변성을 위해 setter를 사용하지 않음
    //지정된 작업 외에는 벼경이 안되도록 하기 위함
    //따로 메서드를 만들어서 지정된 작업을 실행
    public void changeBoard(Board_0306 board) {
        this.board = board;
    }
}
