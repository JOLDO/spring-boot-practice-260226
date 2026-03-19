package com.busanit501.springboot._2_260303.repository;

import com.busanit501.springboot._2_260303.domain.Board_0303;
import com.busanit501.springboot._5_260306.domain.BoardImage;
import com.busanit501.springboot._5_260306.domain.Board_0306;
import com.busanit501.springboot._5_260306.domain.Reply_0306;
import com.busanit501.springboot._5_260306.repository.BoardRepository_0306;
import com.busanit501.springboot._5_260306.repository.ReplyRepository_0306;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class Board0303RepositoryTests {

    @Autowired
    private BoardRepository_0303 boardRepository_0303;
    @Autowired
    private BoardRepository_0306 boardRepository_0306;
    @Autowired
    private ReplyRepository_0306 replyRepository_0306;

    @Test
    public void testInsert() {
        //더미 데이터 100개를 임의로 하드코딩으로 작성, 반복문으로
        IntStream.rangeClosed(1, 100).forEach(i -> {
            //더미 객체 생성
            Board_0303 board_0303 = Board_0303.builder()
                .title("더미 제목...title" + i)
                .content("더미 내용...content" + i)
                .writer("더미 사용자...user" + i % 10)
                .build();

            //boardRepository의 기능인 save메서드로 실제 db에 저장
            Board_0303 result = boardRepository_0303.save(board_0303);
            log.info("결과 확인 : " + result.getBno());
        });
    }

    @Test
    public void testSelect() {
        Long bno = 100L;
        Optional<Board_0303> result = boardRepository_0303.findById(bno);
        Board_0303 board_0303 = result.orElseThrow();
        log.info("board 확인 : " + board_0303);
    }

    @Test
    public void testUpdate() {
        Long bno = 19L;
        Optional<Board_0303> result = boardRepository_0303.findById(bno);
        Board_0303 board_0303 = result.orElseThrow();
        // 수정 메서드를 호출해서, 변경 후, 저장 및 수정.
        board_0303.change("수정 테스트 제목...", "수정 테스트 내용...");
        boardRepository_0303.save(board_0303);
    }

    @Test
    public void testDelete() {
        Long bno = 100L;
        boardRepository_0303.deleteById(bno);
    }

    @Test
    public void testPaging() {
        //1페이지, 정렬 내림차순
        Pageable pageable= PageRequest.of(1, 10, Sort.by("bno").descending());
        Page<Board_0303> result = boardRepository_0303.findAll(pageable);
        //result 결과에는 다양한 페이징 준비물이 들어있음
        log.info("전체 갯수 total count : " + result.getTotalElements());
        log.info("전체 페이지 total page : " + result.getTotalPages());
        log.info("조회 페이지 번호 page number : " + result.getNumber());
        log.info("조회 페이지 크기 page size : " + result.getSize());
        log.info("이전 페이지 여부 : " + result.hasPrevious());
        log.info("다음 페이지 여부 : " + result.hasNext());

        //페이징 처리가된 10개의 데이터 목록
        List<Board_0303> todoList =  result.getContent();
        log.info("페이징 처리가 된 10개의 데이터 확인 : " + todoList);
    }

    @Test
    public void testSearch() {
        //page 2번(page = 1)에서 크기10, 내림차순 정렬
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        boardRepository_0303.search1(pageable);
    }

    @Test
    public void testSearch2() {
        //검색, 페이징 처리
        //준비물 1)검색타입 2)검색어 3)화면에서 전달받은 페이징 처리 준비물
        String[] types = {"t", "c", "w"};
        String keyword = "테스트";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board_0303> result = boardRepository_0303.searchAll(types, keyword, pageable);
        log.info("출력 전체 갯수 total count : " + result.getTotalElements());
        log.info("출력 전체 페이지 total page : " + result.getTotalPages());
        log.info("출력 조회 페이지 번호 page number : " + result.getNumber());
        log.info("출력 조회 페이지 크기 page size : " + result.getSize());
        log.info("출력 이전 페이지 여부 : " + result.hasPrevious());
        log.info("출력 다음 페이지 여부 : " + result.hasNext());

        //페이징 처리가된 10개의 데이터 목록
        List<Board_0303> todoList =  result.getContent();
        log.info("출력 페이징 처리가 된 10개의 데이터 확인 : " + todoList);
    }

    //영속성 테스트 작업1 아직 고아 객체 제거 설정이 없어서 실제 삭제는 안됨
    @Test
    public void testInsterWithImage() {
        Board_0306 board = Board_0306.builder()
                .title("샘플 게시글....")
                .content("샘플 내용....")
                .writer("샘플 작성자....")
                .build();
        //첨부 이미지 3개를 작석해 Board객체에 담기
        for(int i = 0; i < 3; i++) {
            board.addImage(UUID.randomUUID().toString(), "file" + i + ".png");
        }
        boardRepository_0306.save(board);
    }

    @Test
    @Transactional
    public void testReadWithImage() {
//        Optional<Board_0306> result = boardRepository_0306.findById(1L);
        Optional<Board_0306> result = boardRepository_0306.findByIdWithImages(1L);
        Board_0306 board = result.orElseThrow();

        log.info("board 조회 해보기 : " + board);
        log.info("====================================== ");
        // 에러가 발생함. no session

        // 첨부 이미지를 확인
        for( BoardImage boardImage: board.getImageSet()) {
            log.info("게시글에 첨부된 이미지 조회 : " + boardImage);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testModifyImage() {
        Optional<Board_0306> result = boardRepository_0306.findByIdWithImages(1L);
        Board_0306 board = result.orElseThrow();

        //기존 첨부파일 삭제
        board.clearImages();

        //새로운 파일 첨부
        for(int i = 0; i < 3; i++) {
            board.addImage(UUID.randomUUID().toString(), "수정444_file" + i + ".png");
        }

        boardRepository_0306.save(board);
    }

    @Transactional
    @Commit
    @Test
    public void testRemoveAll() {
        replyRepository_0306.deleteByBoard_Bno(1L);
        //게시글 지우기
        boardRepository_0306.deleteById(1L);

    }

    // 약 100 개정도의 게시글과, 댓글, 첨부 이미지 까지만, 더미데이터 추가 해보기.
    @Transactional
    @Commit
    @Test
    public void testInsertAll() {
        for (int i = 1; i <= 100; i++) {
            Board_0306 board = Board_0306.builder()
                    .title("샘플 데이터 " + i)
                    .content("샘플 제목 " + i)
                    .writer("이상용" + i)
                    .build();

            for (int j = 0; j < 3; j++) {
                if (i % 5 == 0) {
                    // 5번째 씩 , 첨부 이미지 추가 안하기.
                    continue;
                }
                // 첨부 이미지 3장씩 더미데이터
                String uuid = UUID.randomUUID().toString();
                String fileName = "샘플 이미지";
                board.addImage(uuid, fileName + j + ".png");


            }
            // 게시글 작성 후 ,
            boardRepository_0306.save(board);
            // 댓글 달기.
            for (int j = 0; j < 3; j++) {
                Reply_0306 reply = Reply_0306.builder()
                        .board(board)
                        .replyText("샘플 댓글" + j)
                        .replyer("이상용")
                        .build();
                replyRepository_0306.save(reply);
            }
        }
    }

    @Transactional
    @Test
    public void testSearchImageReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        boardRepository_0306.searchWithAll(null, null, pageable);
    }
}
