package me.jooeon.mybeauty.global.s3.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.global.s3.model.ImagePrefix;
import me.jooeon.mybeauty.global.s3.model.dto.S3File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Uri;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Util {

    private final S3FileUtil s3FileUtil;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public S3File uploadMultipartFile(MultipartFile file, ImagePrefix imagePrefix) {
        log.info("S3 파일 업로드 시작. [{} of {}]", file.getOriginalFilename(), file.getContentType());
        return uploadS3File(s3FileUtil.convert(file), imagePrefix);
    }

    public S3File uploadS3File(S3File s3File, ImagePrefix imagePrefix) {
        try {
            String key = imagePrefix.getValue() + s3File.getFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(s3File.getContentType().getMimeType())
                    .contentLength(s3File.getContentLength())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(s3File.getBytes()));
            log.info("S3 파일 업로드 완료. [{}]", key);
            return s3File.putObjectUrl(getUrl(s3File.getFilename()));
        } catch (Exception e) {
            e.printStackTrace();

            // todo 커스텀 예외 생성 후 처리 필요
            throw new IllegalArgumentException("S3 파일 업로드 실패");
        }
    }

    private String getUrl(String filename) {
        try {
            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .bucket(bucket)
                    .key(filename)
                    .build();
            return s3Client.utilities().getUrl(getUrlRequest).toString();
        } catch (Exception e) {
            e.printStackTrace();

            // todo 커스텀 예외 생성 필요
            throw new IllegalArgumentException("S3 파일 업로드 실패");
        }
    }

    public void delete(String objectUrl) {
        S3Uri s3Uri = s3Client.utilities().parseUri(URI.create(objectUrl));
        // todo 커스텀 예외 생성
        String filename = s3Uri.key()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 S3 파일입니다."));
        deleteObject(filename);
    }

    public void deleteObject(String filename) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(filename)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
            // todo 커스텀 예외 생성
            throw new IllegalArgumentException("S3 파일 삭제에 실패했습니다.");
        }
    }
}
