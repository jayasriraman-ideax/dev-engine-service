package com.devengine.controller;

import com.devengine.dto.drive.DriveFileListRequest;
import com.devengine.dto.drive.DriveFileListResponse;
import com.devengine.dto.drive.DriveResponseDto;
import com.devengine.services.DriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DriveControllerImpl implements DriveController {

    private final DriveService driveService;


    @Override
    public ResponseEntity<List<DriveResponseDto>> getDrives() {

        List<DriveResponseDto> drives = driveService.getFolders();

        return ResponseEntity.ok(drives);
    }


    @Override
    public ResponseEntity<List<DriveFileListResponse>> getDriveFiles(
            DriveFileListRequest request) {

        List<DriveFileListResponse> files =
                driveService.getFilesFromDrive(request);

        return ResponseEntity.ok(files);
    }


    @Override
    public ResponseEntity<Resource> viewFile(@RequestParam String path) {

        Resource resource = driveService.viewFile(path);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        try {

            Path filePath = Paths.get(path);

            String contentType = Files.probeContentType(filePath);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Override
    public ResponseEntity<Resource> downloadFile(@RequestParam String path) {

        Resource resource = driveService.downloadFile(path);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        try {

            Path filePath = Paths.get(path);
            String fileName = filePath.getFileName().toString();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}