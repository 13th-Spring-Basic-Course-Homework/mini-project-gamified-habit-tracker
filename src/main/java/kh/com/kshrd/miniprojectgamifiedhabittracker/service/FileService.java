package kh.com.kshrd.miniprojectgamifiedhabittracker.service;

import kh.com.kshrd.miniprojectgamifiedhabittracker.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {

    FileMetadata uploadFile(MultipartFile file);

    InputStream getFileByFileName(String fileName);
}
