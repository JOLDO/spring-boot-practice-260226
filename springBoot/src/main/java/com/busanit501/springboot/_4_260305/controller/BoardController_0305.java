package com.busanit501.springboot._4_260305.controller;

import com.busanit501.springboot._4_260305.dto.BoardDTO_0305;
import com.busanit501.springboot._4_260305.dto.PageRequestDTO_0305;
import com.busanit501.springboot._4_260305.dto.PageResponseDTO_0305;
import com.busanit501.springboot._4_260305.service.BoardService_0305;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/_4_260305/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController_0305 {

    private final BoardService_0305 boardService_0305;

    @GetMapping("/list")
    public void list(PageRequestDTO_0305 pageRequestDTO_0305, Model model) {
        PageResponseDTO_0305<BoardDTO_0305> responseDTO = boardService_0305.list(pageRequestDTO_0305);
        log.info("BoardController에서 responseDTO 확인 : " + responseDTO);
        model.addAttribute("responseDTO", responseDTO);

        /*for(String search : TEST) {
            if(decomposeKorean(search).contains(decomposeKorean("사고"))) {
                log.info("출력 : " + search);
            }
        }*/
    }

    //화면제공
    @GetMapping("/register")
    public void registerGet() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO_0305 boardDTO_0305, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("BoardController_0305에서 registerPost 작업중");

        if(bindingResult.hasErrors()) {
            log.info("BoardController_0305에서 registerPost 유효성 오류 발생");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/_4_260305/board/register";
        }
        log.info("boardDTO_0305 확인 : " + boardDTO_0305);
        Long bno = boardService_0305.register(boardDTO_0305);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/_4_260305/board/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long bno, PageRequestDTO_0305 pageRequestDTO_0305, Model model) {
        BoardDTO_0305 boardDTO_0305 = boardService_0305.readOne(bno);
        log.info("BoardController_0305에서 read boardDTO_0305 확인 : " + boardDTO_0305);
        model.addAttribute("dto", boardDTO_0305);
    }

    @PostMapping("/modify")
    public String modifyPost(@Valid BoardDTO_0305 boardDTO_0305, BindingResult bindingResult,
                               PageRequestDTO_0305 pageRequestDTO_0305,
                               RedirectAttributes redirectAttributes) {
        log.info("BoardController_0305에서 modifyPost 작업중");

        if(bindingResult.hasErrors()) {
            log.info("BoardController_0305에서 modifyPost 유효성 오류 발생");
            String link = pageRequestDTO_0305.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO_0305.getBno());
            return "redirect:/_4_260305/board/modify?" + link;
        }
        log.info("boardDTO_0305 확인 : " + boardDTO_0305);
        boardService_0305.modify(boardDTO_0305);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO_0305.getBno());
        return "redirect:/_4_260305/board/read";
    }

    @PostMapping("/remove")
    public String removePost(Long bno, RedirectAttributes redirectAttributes) {
        log.info("BoardController_0305에서 removePost 작업중");

        boardService_0305.remove(bno);
        redirectAttributes.addAttribute("result", "removed");
        return "redirect:/_4_260305/board/list";
    }

    // 한국어 자모 분리 함수
    // 예) "사과" → "ㅅㅏㄱㅗㅏ", "없음" → "ㅇㅓㅂㅅㅇㅡㅁ"
    private static final char[] CHOSUNG  = {'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
    private static final char[] JOONGSUNG = {'ㅏ','ㅐ','ㅑ','ㅒ','ㅓ','ㅔ','ㅕ','ㅖ','ㅗ','ㅘ','ㅙ','ㅚ','ㅛ','ㅜ','ㅝ','ㅞ','ㅟ','ㅠ','ㅡ','ㅢ','ㅣ'};
    private static final char[] JONGSUNG = {' ','ㄱ','ㄲ','ㄳ','ㄴ','ㄵ','ㄶ','ㄷ','ㄹ','ㄺ','ㄻ','ㄼ','ㄽ','ㄾ','ㄿ','ㅀ','ㅁ','ㅂ','ㅄ','ㅅ','ㅆ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
    private static final Map<Character, char[]> map = Map.of(
            'ㅘ', new char[] {'ㅗ', 'ㅏ'},
            'ㅙ', new char[] {'ㅗ', 'ㅐ'},
            'ㅚ', new char[] {'ㅗ', 'ㅣ'},
            'ㅝ', new char[] {'ㅜ', 'ㅓ'},
            'ㅞ', new char[] {'ㅜ', 'ㅔ'},
            'ㅟ', new char[] {'ㅜ', 'ㅣ'},
            'ㅢ', new char[] {'ㅡ', 'ㅣ'}
    );

    private static final String[] TEST = {"사과", "사고", "숙고", "없음", "버섯"};

    /*public static String decomposeKorean(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= 0xAC00 && c <= 0xD7A3) {
                int syllable = c - 0xAC00;
                int cho  = syllable / (21 * 28);
                int jung = (syllable % (21 * 28)) / 28;
                int jong = syllable % 28;
                sb.append(CHOSUNG[cho]);
                char joongSung = JOONGSUNG[jung];
                if(map.containsKey(joongSung)) {
                    sb.append(map.get(joongSung));
                } else {
                    sb.append(JOONGSUNG[jung]);
                }
                if (jong != 0) sb.append(JONGSUNG[jong]);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }*/
}
