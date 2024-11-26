package me.jooeon.mybeauty.domain.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.global.s3.model.dto.S3File;
import me.jooeon.mybeauty.global.s3.utils.S3Util;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Util s3Util;
    @Value("${remove-bg.api-key}")
    private String removeBgApiKey;

    public String upload(MultipartFile file) {
//        MultipartFile bgRemovedMultipartFile = new CustomMultipartFile(
//                file.getName(),
//                file.getOriginalFilename(),
//                file.getContentType(),
//                removeBackground(file)
//        );



//        S3File s3File = s3Util.uploadMultipartFile(bgRemovedMultipartFile);
        S3File s3File = s3Util.uploadMultipartFile(file);


//        try {
//            MultipartFile resizedMultipartFile = new CustomMultipartFile(
//                    file.getName(),
//                    file.getOriginalFilename(),
//                    file.getContentType(),
//                    resizeImage(file.getBytes(), 150, 150)
//            );
//
//            S3File s3File = s3Util.uploadMultipartFile(resizedMultipartFile);
//            log.info("file-path = {}", s3File.getFilename());
//
//            return s3File.getObjectUrl();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        S3File s3File = s3Util.uploadMultipartFile(file);
//        log.info("file-path = {}", s3File.getFilename());

        return s3File.getObjectUrl();
    }

    private byte[] removeBackground(MultipartFile file) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("https://api.remove.bg/v1.0/removebg");

            // 요청 엔티티 구성
            HttpEntity requestEntity = MultipartEntityBuilder.create()
                    .addTextBody("size", "auto")
                    .addBinaryBody("image_file", file.getInputStream(),
                            ContentType.APPLICATION_OCTET_STREAM, file.getOriginalFilename())
                    .build();
            post.setEntity(requestEntity);
            post.setHeader("X-Api-Key", removeBgApiKey);

            // HttpClientResponseHandler 사용
            HttpClientResponseHandler<byte[]> responseHandler = response -> {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    return EntityUtils.toByteArray(response.getEntity());
                } else {
                    throw new RuntimeException("Background removal failed with status code: " + statusCode);
                }
            };

            // 요청 실행 및 응답 처리
            return httpClient.execute(post, responseHandler);
        } catch (IOException e) {
            throw new RuntimeException("Error during background removal", e);
        }
    }

    public byte[] resizeImage(byte[] imageBytes, int width, int height) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(new ByteArrayInputStream(imageBytes))
                .size(width, height)
                .outputFormat("jpg")
                .toOutputStream(outputStream);
        return outputStream.toByteArray();
    }
}
