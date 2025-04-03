package com.can.easyquiz.service.impl;

import com.can.easyquiz.service.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadImpl implements FileUpload {
    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public String saveFile(InputStream inputStream, long size, String extName) throws IOException {
        // 验证文件大小
        if (size > 10 * 1024 * 1024) {
            throw new IOException("文件大小超过 10MB 限制");
        }

        // 确保上传目录存在
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 生成唯一文件名
        String filename = UUID.randomUUID().toString() + "." + extName;
        Path filePath = uploadPath.resolve(filename);

        // 保存文件
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }
}