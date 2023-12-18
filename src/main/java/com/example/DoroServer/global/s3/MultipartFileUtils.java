package com.example.DoroServer.global.s3;

import org.springframework.web.multipart.MultipartFile;

import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;

public class MultipartFileUtils {
    public static void validateMultipartFiles(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw new BaseException(Code.JSON_SYNTAX_ERROR);
        }
        if (files.length > 100 || files.length < 1) {
            throw new BaseException(Code.LECTURE_CONTENT_INVAILD_IMAGE_COUNT);
        }
        for (MultipartFile file : files) {
            if (file.getSize() > 10485760) { // 10MB
                throw new BaseException(Code.LECTURE_CONTENT_IMAGE_SIZE_OVER);
            }
        }
    }
}
