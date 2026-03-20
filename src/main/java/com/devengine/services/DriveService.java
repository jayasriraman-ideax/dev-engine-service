package com.devengine.services;


import com.devengine.dto.drive.DriveFileListRequest;
import com.devengine.dto.drive.DriveFileListResponse;
import com.devengine.dto.drive.DriveResponseDto;

import org.springframework.core.io.Resource;
import java.util.List;

public interface DriveService {

    List<DriveResponseDto> getFolders();

    List<DriveFileListResponse> getFilesFromDrive(DriveFileListRequest request);

    Resource viewFile(String path);

    Resource downloadFile(String path);

}