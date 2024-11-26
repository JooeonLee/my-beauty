package me.jooeon.mybeauty.global.s3.utils;

import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.global.s3.model.ContentType;
import me.jooeon.mybeauty.global.s3.model.dto.S3File;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class S3FileUtil {

    public S3File convert(MultipartFile multipartFile)  {

        // todo 커스텀 예외 생성 후 처리 필요
        log.info("멀티파트파일 content type = {}", multipartFile.getContentType());
        ContentType contentType = getContentType(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 파일 타입임"));

        try {
            return S3File.builder()
                    .filename(createCleanedFileName(multipartFile.getOriginalFilename(), contentType))
                    .contentType(contentType)
                    .contentLength(multipartFile.getSize())
                    .bytes(multipartFile.getBytes())
                    .build();
        } catch (Exception e) {
            // todo 커스텀 예외 생성 후 IOexception 잡아서 처리 필요
            throw new IllegalArgumentException("파일읽기에 실패함");
        }
    }

    private Optional<ContentType> getContentType(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        log.info("{}", extension);
        return ContentType.getByExtension(extension);
    }

    private String createCleanedFileName(String fileName, ContentType contentType) {

        String cleanedFileName = fileName.replaceAll("[/\\\\\\s]+", "_");
        return contentType.getFilePath() + createFileName(fileName);
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID() + "_" + fileName;
    }
}
