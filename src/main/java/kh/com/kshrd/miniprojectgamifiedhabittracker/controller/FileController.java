package kh.com.kshrd.miniprojectgamifiedhabittracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.APIResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.FileMetadata;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Upload a file", description = "Uploads a file and returns metadata about the uploaded file.")
    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<FileMetadata>> uploadFile(@RequestParam MultipartFile file) {
        FileMetadata fileMetadata = fileService.uploadFile(file);
        APIResponse<FileMetadata> apiResponse = APIResponse.<FileMetadata>builder()
                .success(true)
                .message("File uploaded successfully! Metadata of the uploaded file is returned.")
                .status(HttpStatus.CREATED)
                .payload(fileMetadata)
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "Preview a file", description = "Fetches a file by its name and returns the file as a byte array (usually for image previews).")
    @GetMapping("/preview-file/{file-name}")
    public ResponseEntity<?> getFileByFileName(@PathVariable("file-name") String fileName) throws IOException {
        InputStream inputStream = fileService.getFileByFileName(fileName);
        byte[] fileContent = inputStream.readAllBytes();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(fileContent);
    }

}
