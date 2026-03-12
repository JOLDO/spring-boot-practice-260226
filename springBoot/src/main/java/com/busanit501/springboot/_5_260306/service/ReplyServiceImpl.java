package com.busanit501.springboot._5_260306.service;

import com.busanit501.springboot._5_260306.domain.Board_0306;
import com.busanit501.springboot._5_260306.domain.Reply_0306;
import com.busanit501.springboot._5_260306.dto.PageRequestDTO_0306;
import com.busanit501.springboot._5_260306.dto.PageResponseDTO_0306;
import com.busanit501.springboot._5_260306.dto.ReplyDTO_0306;
import com.busanit501.springboot._5_260306.repository.BoardRepository_0306;
import com.busanit501.springboot._5_260306.repository.ReplyRepository_0306;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    @Qualifier("getMapper_0306")
    private final ModelMapper modelMapper;
    private final ReplyRepository_0306 replyRepository;
    private final BoardRepository_0306 boardRepository;

    @Override
    public Long register(ReplyDTO_0306 replyDTO) {
        Reply_0306 reply = modelMapper.map(replyDTO, Reply_0306.class);
        Optional<Board_0306> result = boardRepository.findById(replyDTO.getBno());
        Board_0306 board = result.orElseThrow();
        reply.changeBoard(board);
        Long rno = replyRepository.save(reply).getRno();
        return rno;
    }

    @Override
    public ReplyDTO_0306 read(Long rno) {
        Optional<Reply_0306> replyOptional = replyRepository.findById(rno);
        Reply_0306 reply = replyOptional.orElseThrow();
        ReplyDTO_0306 replyDTO = modelMapper.map(reply, ReplyDTO_0306.class);
        return replyDTO;
    }

    @Override
    public void modify(ReplyDTO_0306 replyDTO) {
        Optional<Reply_0306> replyOptional = replyRepository.findById(replyDTO.getRno());
        Reply_0306 reply = replyOptional.orElseThrow();
        reply.changeText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO_0306<ReplyDTO_0306> getListOfBoard(Long bno, PageRequestDTO_0306 pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("rno").ascending());
        Page<Reply_0306> result = replyRepository.listOfBoard(bno, pageable);
        List<ReplyDTO_0306> dtoList = result.getContent().stream().map(reply -> modelMapper.map(reply, ReplyDTO_0306.class)).collect(Collectors.toList());
        int total = (int)result.getTotalElements();
        PageResponseDTO_0306<ReplyDTO_0306> pageResponseDTO = PageResponseDTO_0306.<ReplyDTO_0306>withAll().pageRequestDTO(pageRequestDTO).dtoList(dtoList).total(total).build();
        return pageResponseDTO;
    }
}
