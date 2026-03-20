package com.devengine.services;

import com.devengine.dto.drive.DriveFileListRequest;
import com.devengine.dto.drive.DriveFileListResponse;
import com.devengine.dto.drive.DriveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

@Service
@RequiredArgsConstructor
public class DriveServiceImpl implements DriveService {

    private static final double GB = 1024 * 1024 * 1024;

    @Override
    public List<DriveResponseDto> getFolders() {

        List<DriveResponseDto> driveList = new ArrayList<>();

        File[] roots = File.listRoots();

        if (roots != null) {

            for (File root : roots) {

                String driveName = root.getAbsolutePath();

                if ("C:\\".equalsIgnoreCase(driveName)) {
                    continue;
                }

                DriveResponseDto response = new DriveResponseDto();

                double total = root.getTotalSpace() / GB;
                double free = root.getFreeSpace() / GB;
                double used = total - free;

                response.setDriveName(driveName);
                response.setTotalSpace(Math.round(total * 10.0) / 10.0);
                response.setFreeSpace(Math.round(free * 10.0) / 10.0);
                response.setUsedSpace(Math.round(used * 10.0) / 10.0);

                driveList.add(response);
            }
        }

        return driveList;
    }

    @Override
    public List<DriveFileListResponse> getFilesFromDrive(DriveFileListRequest request) {

        List<DriveFileListResponse> fileList = new ArrayList<>();
//
        File folder = new File(request.getDriveName());

        File[] files = folder.listFiles();

        if (files != null) {

            for (File file : files) {

                DriveFileListResponse response = new DriveFileListResponse();

                response.setName(file.getName());
                response.setPath(file.getAbsolutePath());
                response.setDirectory(file.isDirectory());
                response.setSize(file.length());

                fileList.add(response);
            }
        }

        return fileList;
    }

    @Override
    public Resource viewFile(String path) {

        try {

            Path filePath = Paths.get(path);

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Resource downloadFile(String path) {

        try {

            Path filePath = Paths.get(path);

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}