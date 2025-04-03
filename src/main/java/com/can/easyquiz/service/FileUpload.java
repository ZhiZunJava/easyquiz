package com.can.easyquiz.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface FileUpload {
    String saveFile(InputStream inputStream, long size, String extName) throws IOException;
}