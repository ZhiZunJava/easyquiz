package com.can.easyquiz.controller.student;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.service.FileUpload;
import com.can.easyquiz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;


@RequestMapping("/api/student/upload")
@RestController("StudentUploadController")
public class UploadController extends ApiController {

    private final FileUpload fileUpload;
    private final UserService userService;

    @Autowired
    public UploadController(FileUpload fileUpload, UserService userService) {
        this.fileUpload = fileUpload;
        this.userService = userService;
    }


    @RequestMapping("/image")
    @ResponseBody
    public RestResponse questionUploadAndReadExcel(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        long attachSize = multipartFile.getSize();
        String imgName = multipartFile.getOriginalFilename();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String filePath = fileUpload.saveFile(inputStream, attachSize, imgName);
            userService.changePicture(getCurrentUser(), filePath);
            return RestResponse.ok(filePath);
        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }
    }


}