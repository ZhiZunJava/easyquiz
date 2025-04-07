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
    public RestResponse<String> questionUploadAndReadExcel(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");

        if (multipartFile == null || multipartFile.isEmpty()) {
            return RestResponse.fail(1, "请选择要上传的文件");
        }

        long attachSize = multipartFile.getSize();
        String imgName = multipartFile.getOriginalFilename();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 保存文件并获取相对路径
            String relativePath = fileUpload.saveFile(inputStream, attachSize, imgName);

            // 构建完整URL
            String fullUrl = buildFullUrl(request, relativePath);

            // 更新用户头像
            userService.changePicture(getCurrentUser(), fullUrl);

            return RestResponse.ok(fullUrl);
        } catch (IOException e) {
            return RestResponse.fail(2, "文件处理失败: " + e.getMessage());
        }
    }

    private String buildFullUrl(HttpServletRequest request, String relativePath) {
        String scheme = request.getScheme();             // http / https
        String serverName = request.getServerName();     // 域名或IP
        int serverPort = request.getServerPort();        // 端口
        String contextPath = request.getContextPath();   // 应用上下文路径

        // 处理默认端口（80/443不需要显示端口）
        String portPart = (serverPort == 80 || serverPort == 443) ? "" : ":" + serverPort;

        // 拼接完整URL
        return String.format("%s://%s%s%s%s",
                scheme,
                serverName,
                portPart,
                contextPath,
                relativePath.startsWith("/") ? relativePath : "/" + relativePath);
    }


}