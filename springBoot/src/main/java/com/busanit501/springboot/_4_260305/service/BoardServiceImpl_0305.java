package com.busanit501.springboot._4_260305.service;

import com.busanit501.springboot._4_260305.domain.Board_0305;
import com.busanit501.springboot._4_260305.dto.BoardDTO_0305;
import com.busanit501.springboot._4_260305.dto.PageRequestDTO_0305;
import com.busanit501.springboot._4_260305.dto.PageResponseDTO_0305;
import com.busanit501.springboot._4_260305.repository.BoardRepository_0305;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional  //작업후 commit/rollback을 위해서
public class BoardServiceImpl_0305 implements BoardService_0305 {
//화면으로부터 글작성 재료를 DTO에 받아서 엔티티 객체 타입으로 변환해서 전달하는 용도
    @Qualifier("getMapper_0305")
    private final ModelMapper modelMapper;  //DTO -> Entity 객체 변환
    private final BoardRepository_0305 boardRepository_0305;    //실제 DB에 일을 시키는 기능

    @Override
    public Long register(BoardDTO_0305 boardDTO_0305) {
        Board_0305 board = modelMapper.map(boardDTO_0305, Board_0305.class);
        Long bno = boardRepository_0305.save(board).getBno();
        return bno;
    }

    @Override
    public BoardDTO_0305 readOne(Long bno) {
        Optional<Board_0305> result = boardRepository_0305.findById(bno);
        Board_0305 board = result.orElseThrow();
        BoardDTO_0305 boardDTO = modelMapper.map(board, BoardDTO_0305.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO_0305 boardDTO) {
        //1)디비에서 데이터를 가져옴후
        Optional<Board_0305> result = boardRepository_0305.findById(boardDTO.getBno());
        Board_0305 board = result.orElseThrow();

        //2)가져온 내용을 화면에서 변경할 내용으로 교체
        board.change(boardDTO.getTitle(), boardDTO.getContent());

        //3)저장(수정)
        boardRepository_0305.save(board);
    }

    @Override
    public void remove(Long dno) {
        boardRepository_0305.deleteById(dno);
    }

    @Override
    public PageResponseDTO_0305<BoardDTO_0305> list(PageRequestDTO_0305 pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");
        //result안에는 페이징 준비물이 들어있음
        Page<Board_0305> result =  boardRepository_0305.searchAll(types, keyword, pageable);

        //페이징 처리가 된 데이터 10개 목록 + 전체 갯수
        List<BoardDTO_0305> dtoList =  result.getContent().stream()
            .map(board0305 -> modelMapper.map(board0305, BoardDTO_0305.class))
            .collect(Collectors.toList());

        int total = (int)result.getTotalElements();

        //PageResponseDTO_0305타입으로 객체 생성
        PageResponseDTO_0305<BoardDTO_0305> pageResponseDTO = PageResponseDTO_0305.<BoardDTO_0305>withAll()
            .pageRequestDTO(pageRequestDTO)
            .dtoList(dtoList)
            .total(total)
            .build();
        return pageResponseDTO;
    }
}
