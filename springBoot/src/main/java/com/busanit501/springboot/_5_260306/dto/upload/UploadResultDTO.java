package com.busanit501.springboot._5_260306.dto.upload;

import com.busanit501.springboot._5_260306.dto.PageResponseDTO_0306;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private boolean img;    //이미지 여부 확인

    public String getLink() {
        if(img) {
            return "s_" + uuid + "_" + fileName;
        } else {
            return uuid + "_" + fileName;
        }
    }
}
