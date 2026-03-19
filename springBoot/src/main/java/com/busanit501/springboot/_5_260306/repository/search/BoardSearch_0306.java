package com.busanit501.springboot._5_260306.repository.search;

import com.busanit501.springboot._5_260306.domain.Board_0306;
import com.busanit501.springboot._5_260306.dto.BoardListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch_0306 {
    //검색의 결과도 페이징 처리를 할 예정
    Page<Board_0306>  search1(Pageable pageable);

    //검색어(제목/내용), 페이징 처리 적용
    /**
     * 검색 조건과 페이징을 적용해서
     게시글 목록을 조회한다.
     *
     * @param types   검색 타입 배열 (예:
    {"t", "c", "w"} - 제목, 내용, 작성자)
     * @param keyword 검색어
     * @param pageable 페이징 정보 (페이지
    번호, 사이즈, 정렬)
     * @return 페이징 처리된 게시글 목록
    {@link Page}
     */
    Page<Board_0306> searchAll(String[] types, String keyword, Pageable pageable);

    //페이징 + 검색 + 댓글의 갯수 포함
    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    //N+1테스트
    Page<BoardListReplyCountDTO> searchWithAll(String[] types, String keyword, Pageable pageable);

}
