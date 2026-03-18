package com.busanit501.springboot._5_260306.controller;

import com.busanit501.springboot._5_260306.dto.upload.UploadFileDTO;
import com.busanit501.springboot._5_260306.dto.upload.UploadResultDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UpDownController {

    @Value("${com.busanit501.upload.path}")
    private String uploadPath;

    @Tag(name = "이미지파일 업로드 테스트",
            description = "멀티파트 폼에 이미지 첨부해서 서버에 전달함, post 형식으로")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @GetMapping(value = "/list")
    public List<UploadResultDTO> upload(
//            Long bno, //이렇게 하면 bno를 파람으로 받을수 있을거 같은데
            UploadFileDTO uploadFileDTO
    ) {
        log.info("UpDownController 이미지 첨부 확인 : ");
        // 확인용, 더미 데이터 ,

        if(uploadFileDTO.getFiles() != null) {
            final List<UploadResultDTO> list = new ArrayList<>();

            uploadFileDTO.getFiles().forEach(file -> {

                String originName = file.getOriginalFilename();
                log.info(originName);
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid + "_" + originName);
                boolean image = false;
                try {
                    file.transferTo(savePath);


                    if(Files.probeContentType(savePath).startsWith("image")) {
                        image = true;
                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originName);
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                list.add(
                        UploadResultDTO.builder()
                                .uuid(uuid)
                                .fileName(originName)
                                .img(image)
                                .build()
                );
            });
            return list;
        }
        return null;
    }

    @Tag(name = "이미지파일 조회 테스트",
            description = "멀티파트 폼에 이미지 확인하기, get 형식으로")
    @GetMapping(value = "/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName) {
        //받은 파일 이름을 이용해서 데이터에 접근해서 http 데이터 body에 담아서 전달
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName); //실제 파일의 경로로 만드는 것
        String resourceName = resource.getFilename();
        log.info("UpDownController resourceName : " + resourceName);

        //http통신 프로토콜로 보냄
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Tag(name = "이미지파일 삭제 테스트",
            description = "멀티파트 폼에 이미지 삭제하기, delete 형식으로")
    @DeleteMapping(value = "/remove/{fileName}")
    public Map<String, Boolean> removeFile(@PathVariable String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName); //실제 파일의 경로로 만드는 것
        String resourceName = resource.getFilename();
        log.info("UpDownController resourceName : " + resourceName);

        Map<String, Boolean> resultMap = new HashMap<>();
        boolean remove = false;

        try {
            //첨부 파일이 이미지 이면 image/jpg이런식으로 지정
            String contentType = Files.probeContentType(resource.getFile().toPath());
            //파일 삭제후 성공시 true반환
            remove = resource.getFile().delete();   //원본 이미지 삭제
            if(contentType.startsWith("image")) {
                //썸네이파일 찾아서 삭제
                File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                thumbnailFile.delete();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        resultMap.put("result",  remove);

        return resultMap;
    }
}
