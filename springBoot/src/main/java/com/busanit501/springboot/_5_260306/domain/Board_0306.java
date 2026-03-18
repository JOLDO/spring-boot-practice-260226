package com.busanit501.springboot._5_260306.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="board")
//추가기능 : 어노테이션 옵션 추가
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet") //Board에서 객체를 문자열로 출력시 첨부된 이미지 들은 출력을 안함
public class Board_0306 extends BaseEntity_0306 {  //베이스 엔티티를 상속 받으면 됨
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
    //@Setter 대신 수정 전용 메서드를 만들어서 변경 가능한 필드(title, content)만 명시적으로 수정(save하면 같은 id라면 update됨)
    //setter를 사용하지 않아서 변수의 직접 수정을 막고, 필요부분만 수정하기 위해
    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //추가 작업(연관관계 설정으로 보드이미지와 보드는 양방향성이 됨)
    //cascade 연좌제 종류, 필요할때 불러옴(lazy)
    // @OneToMany
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)  //"board"는 BoardImage의 board 변수를 의미 하고 BoardImage가 주인이라는 뜻
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>(); //중복제거를 위해(null로 되기 때문에 디폴트 어노테이션 써줌)

    //이미지 추가 메서드
    public void addImage(String uuid, String fileName) {
        //BoardImage 객체 생성
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(boardImage);
    }

    //이미지 삭제 메서드
    public void clearImages() {
        imageSet.forEach(boardImage -> {
            boardImage.changeBoard(null);
        });
    }
}
