package me.jooeon.mybeauty.global.s3.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.global.s3.model.ContentType;

@Getter
@NoArgsConstructor
public class S3File {

    private String filename;
    private ContentType contentType;
    private Long contentLength;
    private byte[] bytes;
    private String objectUrl;

    @Builder
    public S3File(String filename, ContentType contentType, Long contentLength, byte[] bytes, String objectUrl) {
        this.filename = filename;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.bytes = bytes;
        this.objectUrl = objectUrl;
    }

    public S3File putObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
        return this;
    }


}
