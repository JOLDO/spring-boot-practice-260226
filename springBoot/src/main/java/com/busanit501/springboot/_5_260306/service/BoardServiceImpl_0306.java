package com.busanit501.springboot._5_260306.service;

import com.busanit501.springboot._5_260306.domain.Board_0306;
import com.busanit501.springboot._5_260306.dto.BoardDTO_0306;
import com.busanit501.springboot._5_260306.dto.PageRequestDTO_0306;
import com.busanit501.springboot._5_260306.dto.PageResponseDTO_0306;
import com.busanit501.springboot._5_260306.repository.BoardRepository_0306;
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
public class BoardServiceImpl_0306 implements BoardService_0306 {
//화면으로부터 글작성 재료를 DTO에 받아서 엔티티 객체 타입으로 변환해서 전달하는 용도
    @Qualifier("getMapper_0306")
    private final ModelMapper modelMapper;  //DTO -> Entity 객체 변환
    private final BoardRepository_0306 boardRepository_0306;    //실제 DB에 일을 시키는 기능

    @Override
    public Long register(BoardDTO_0306 boardDTO_0306) {
        Board_0306 board = modelMapper.map(boardDTO_0306, Board_0306.class);
        Long bno = boardRepository_0306.save(board).getBno();
        return bno;
    }

    @Override
    public BoardDTO_0306 readOne(Long bno) {
        Optional<Board_0306> result = boardRepository_0306.findById(bno);
        Board_0306 board = result.orElseThrow();
        BoardDTO_0306 boardDTO = modelMapper.map(board, BoardDTO_0306.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO_0306 boardDTO) {
        //1)디비에서 데이터를 가져옴후
        Optional<Board_0306> result = boardRepository_0306.findById(boardDTO.getBno());
        Board_0306 board = result.orElseThrow();

        //2)가져온 내용을 화면에서 변경할 내용으로 교체
        board.change(boardDTO.getTitle(), boardDTO.getContent());

        //3)저장(수정)
        boardRepository_0306.save(board);
    }

    @Override
    public void remove(Long dno) {
        boardRepository_0306.deleteById(dno);
    }

    @Override
    public PageResponseDTO_0306<BoardDTO_0306> list(PageRequestDTO_0306 pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");
        //result안에는 페이징 준비물이 들어있음
        Page<Board_0306> result =  boardRepository_0306.searchAll(types, keyword, pageable);

        //페이징 처리가 된 데이터 10개 목록 + 전체 갯수
        List<BoardDTO_0306> dtoList =  result.getContent().stream()
            .map(board0306 -> modelMapper.map(board0306, BoardDTO_0306.class))
            .collect(Collectors.toList());

        int total = (int)result.getTotalElements();

        //PageResponseDTO_0306타입으로 객체 생성
        PageResponseDTO_0306<BoardDTO_0306> pageResponseDTO = PageResponseDTO_0306.<BoardDTO_0306>withAll()
            .pageRequestDTO(pageRequestDTO)
            .dtoList(dtoList)
            .total(total)
            .build();
        return pageResponseDTO;
    }
}
