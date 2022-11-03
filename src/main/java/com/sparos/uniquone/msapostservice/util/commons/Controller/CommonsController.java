package com.sparos.uniquone.msapostservice.util.commons.Controller;

import com.sparos.uniquone.msapostservice.util.commons.service.CommonsService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/commons")
@RequiredArgsConstructor
public class CommonsController {
    private final CommonsService commonsService;

    private final AwsS3UploaderService awsS3UploaderService;

    @GetMapping("/info")
    public ResponseEntity<SuccessResponse> getCommonUserInfoWithToken(HttpServletRequest request){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,commonsService.getCommonsUserInfoByToken(request)));
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<SuccessResponse> insertPostImgDummy(MultipartFile[] multipartFiles) throws IOException {

        if(multipartFiles.length == 0)
            return null;

        List<String> imgNameList = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            String imgName = awsS3UploaderService.upload(file, "uniquoneimg", "img");
            imgNameList.add(imgName);
        }

        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, imgNameList));
    }
}
