package com.busanit501.springboot._5_260306.controller;

import com.busanit501.springboot._5_260306.dto.BoardDTO_0306;
import com.busanit501.springboot._5_260306.dto.PageRequestDTO_0306;
import com.busanit501.springboot._5_260306.dto.PageResponseDTO_0306;
import com.busanit501.springboot._5_260306.service.BoardService_0306;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/_5_260306/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController_0306 {

    private final BoardService_0306 boardService_0306;

    @GetMapping("/list")
    public void list(PageRequestDTO_0306 pageRequestDTO_0306, Model model) {
        PageResponseDTO_0306<BoardDTO_0306> responseDTO = boardService_0306.list(pageRequestDTO_0306);
        log.info("BoardController에서 responseDTO 확인 : " + responseDTO);
        model.addAttribute("responseDTO", responseDTO);

        for(String search : TEST2) {
            if(search.contains(decomposeKorean("ㅂㅓ"))) {
                log.info("출력 : " + search);
            }
        }
    }

    //화면제공
    @GetMapping("/register")
    public void registerGet() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO_0306 boardDTO_0306, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("BoardController_0306에서 registerPost 작업중");

        if(bindingResult.hasErrors()) {
            log.info("BoardController_0306에서 registerPost 유효성 오류 발생");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/_5_260306/board/register";
        }
        log.info("boardDTO_0306 확인 : " + boardDTO_0306);
        Long bno = boardService_0306.register(boardDTO_0306);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/_5_260306/board/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long bno, PageRequestDTO_0306 pageRequestDTO_0306, Model model) {
        BoardDTO_0306 boardDTO_0306 = boardService_0306.readOne(bno);
        log.info("BoardController_0306에서 read boardDTO_0306 확인 : " + boardDTO_0306);
        model.addAttribute("dto", boardDTO_0306);
    }

    @PostMapping("/modify")
    public String modifyPost(@Valid BoardDTO_0306 boardDTO_0306, BindingResult bindingResult,
                             PageRequestDTO_0306 pageRequestDTO_0306,
                             RedirectAttributes redirectAttributes) {
        log.info("BoardController_0306에서 modifyPost 작업중");

        if(bindingResult.hasErrors()) {
            log.info("BoardController_0306에서 modifyPost 유효성 오류 발생");
            String link = pageRequestDTO_0306.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO_0306.getBno());
            return "redirect:/_5_260306/board/modify?" + link;
        }
        log.info("boardDTO_0306 확인 : " + boardDTO_0306);
        boardService_0306.modify(boardDTO_0306);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO_0306.getBno());
        return "redirect:/_5_260306/board/read";
    }

    @PostMapping("/remove")
    public String removePost(Long bno, RedirectAttributes redirectAttributes) {
        log.info("BoardController_0306에서 removePost 작업중");

        boardService_0306.remove(bno);
        redirectAttributes.addAttribute("result", "removed");
        return "redirect:/_5_260306/board/list";
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
    private static final String[] TEST2 = {"ㅅㅏㄱㅗㅏ", "ㅅㅏㄱㅗ", "ㅅㅜㄱㄱㅗ", "ㅇㅓㅄㅇㅡㅁ", "ㅂㅓㅅㅓㅅ", "ㅂㅅ"};

    public static String decomposeKorean(String text) {
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
    }
}
