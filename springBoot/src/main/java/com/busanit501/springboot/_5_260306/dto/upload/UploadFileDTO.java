package com.busanit501.springboot._5_260306.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadFileDTO {
    //MultipartFile => 데이터 + 파일
    private List<MultipartFile> files;
}
