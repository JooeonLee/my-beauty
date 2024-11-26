package me.jooeon.mybeauty.domain.Image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("")
    public BaseResponse<String> upload(@RequestPart("file") MultipartFile file) {
        log.info("=== Image Controller 진입 ===");
        String url = imageService.upload(file);

        return new BaseResponse<>(url);
    }

}
