package com.sparos.uniquone.msapostservice.util.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AwsS3UploaderController {
    private final AwsS3UploaderService awsS3UploaderService;

    @GetMapping("/image")
    public String image() {
        return "image-upload";
    }


    @PostMapping("/image-upload")
    @ResponseBody
    public String imageUpload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img");
    }
}
