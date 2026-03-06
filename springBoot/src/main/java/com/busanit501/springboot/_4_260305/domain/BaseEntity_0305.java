package com.busanit501.springboot._4_260305.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass   //부모클래스 명시
//CRUD를 감지해서 db에 넣기전에 가로채 Spring Data JPA에서 제공하는 '감시 전용 클래스'를 사용하겠다는 뜻
//@CreatedDate나 @LastModifiedDate같은 어노테이이 붙은걸 자동 주입해주겠다는 뜻
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity_0305 {
    //설계 클래스 목적으로 사용

    //생성 시간 필드
    @CreatedDate    //생성된 시간으로 값을씀
    @Column(name = "regDate", updatable = false)    //컬럼명과 변경불가
    private LocalDateTime regDate;

    //수정 시간 필드
    @LastModifiedDate   //마지막 수정된 시간으로 값을 씀
    @Column(name= "modDate")
    private LocalDateTime modDate;


}
