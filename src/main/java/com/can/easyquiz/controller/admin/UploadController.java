package com.can.easyquiz.controller.admin;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.config.property.SystemConfig;
import com.can.easyquiz.service.FileUpload;
import com.can.easyquiz.service.UserService;
import com.can.easyquiz.viewmodel.admin.file.UeditorConfigVM;
import com.can.easyquiz.viewmodel.admin.file.UploadResultVM;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@RequestMapping("/api/admin/upload")
@RestController("AdminUploadController")
public class UploadController extends ApiController {

    private final FileUpload fileUpload;
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private static final String IMAGE_UPLOAD = "imgUpload";
    private static final String IMAGE_UPLOAD_FILE = "upFile";
    private final UserService userService;

    @Autowired
    public UploadController(FileUpload fileUpload, SystemConfig systemConfig, UserService userService) {
        this.fileUpload = fileUpload;
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping("/configAndUpload")
    public Object upload(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if (action.equals(IMAGE_UPLOAD)) {
            try {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                MultipartFile multipartFile = multipartHttpServletRequest.getFile(IMAGE_UPLOAD_FILE);
                long attachSize = multipartFile.getSize();
                String imgName = multipartFile.getOriginalFilename();
                String filePath;
                try (InputStream inputStream = multipartFile.getInputStream()) {
                    filePath = fileUpload.saveFile(inputStream, attachSize, imgName);
                }
                String imageType = imgName.substring(imgName.lastIndexOf("."));
                UploadResultVM uploadResultVM = new UploadResultVM();
                uploadResultVM.setOriginal(imgName);
                uploadResultVM.setName(imgName);
                uploadResultVM.setUrl(filePath);
                uploadResultVM.setSize(multipartFile.getSize());
                uploadResultVM.setType(imageType);
                uploadResultVM.setState("SUCCESS");
                return uploadResultVM;
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            return getUeditorConfigVM();
        }
        return null;
    }

    private static @NotNull UeditorConfigVM getUeditorConfigVM() {
        UeditorConfigVM ueditorConfigVM = new UeditorConfigVM();
        ueditorConfigVM.setImageActionName(IMAGE_UPLOAD);
        ueditorConfigVM.setImageFieldName(IMAGE_UPLOAD_FILE);
        ueditorConfigVM.setImageMaxSize(2048000L);
        ueditorConfigVM.setImageAllowFiles(Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp"));
        ueditorConfigVM.setImageCompressEnable(true);
        ueditorConfigVM.setImageCompressBorder(1600);
        ueditorConfigVM.setImageInsertAlign("none");
        ueditorConfigVM.setImageUrlPrefix("");
        ueditorConfigVM.setImagePathFormat("");
        return ueditorConfigVM;
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