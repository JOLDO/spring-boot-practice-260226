package com.busanit501.springboot._3_260304.controller;

import com.busanit501.springboot._3_260304.dto.BoardDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageRequestDTO_0304;
import com.busanit501.springboot._3_260304.dto.PageResponseDTO_0304;
import com.busanit501.springboot._3_260304.service.BoardService_0304;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/_3_260304/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController_0304 {

    private final BoardService_0304 boardService_0304;

    @GetMapping("/list")
    public void list(PageRequestDTO_0304 pageRequestDTO_0304, Model model) {
        PageResponseDTO_0304<BoardDTO_0304> responseDTO = boardService_0304.list(pageRequestDTO_0304);
        log.info("BoardController에서 responseDTO 확인 : " + responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }
}
