package com.busanit501.springboot._3_260304.service;

import com.busanit501.springboot._3_260304.domain.Board_0304;
import com.busanit501.springboot._3_260304.dto.BoardDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageRequestDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageResponseDTO_0304;
import com.busanit501.springboot._3_260304.repository.BoardRepository_0304;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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
public class BoardServiceImpl_0304 implements BoardService_0304 {
//화면으로부터 글작성 재료를 DTO에 받아서 엔티티 객체 타입으로 변환해서 전달하는 용도
    private final ModelMapper modelMapper;  //DTO -> Entity 객체 변환
    private final BoardRepository_0304 boardRepository_0304;    //실제 DB에 일을 시키는 기능

    @Override
    public Long register(BoardDTO_0304 boardDTO_0304) {
        Board_0304 board = modelMapper.map(boardDTO_0304, Board_0304.class);
        Long bno = boardRepository_0304.save(board).getBno();
        return bno;
    }

    @Override
    public BoardDTO_0304 readOne(Long bno) {
        Optional<Board_0304> result = boardRepository_0304.findById(bno);
        Board_0304 board = result.orElseThrow();
        BoardDTO_0304 boardDTO = modelMapper.map(board, BoardDTO_0304.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO_0304 boardDTO) {
        //1)디비에서 데이터를 가져옴후
        Optional<Board_0304> result = boardRepository_0304.findById(boardDTO.getBno());
        Board_0304 board = result.orElseThrow();

        //2)가져온 내용을 화면에서 변경할 내용으로 교체
        board.change(boardDTO.getTitle(), boardDTO.getContent());

        //3)저장(수정)
        boardRepository_0304.save(board);
    }

    @Override
    public void remove(Long dno) {
        boardRepository_0304.deleteById(dno);
    }

    @Override
    public PageResponseDTO_0304<BoardDTO_0304> list(PageRequestDTO_0304 pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");
        //result안에는 페이징 준비물이 들어있음
        Page<Board_0304> result =  boardRepository_0304.searchAll(types, keyword, pageable);

        //페이징 처리가 된 데이터 10개 목록 + 전체 갯수
        List<BoardDTO_0304> dtoList =  result.getContent().stream()
            .map(board0304 -> modelMapper.map(board0304, BoardDTO_0304.class))
            .collect(Collectors.toList());

        int total = (int)result.getTotalElements();

        //PageResponseDTO_0304타입으로 객체 생성
        PageResponseDTO_0304<BoardDTO_0304> pageResponseDTO = PageResponseDTO_0304.<BoardDTO_0304>withAll()
            .pageRequestDTO(pageRequestDTO)
            .dtoList(dtoList)
            .total(total)
            .build();
        return pageResponseDTO;
    }
}
