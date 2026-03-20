package com.devengine.controller;

import com.devengine.dto.drive.DriveFileListRequest;
import com.devengine.dto.drive.DriveFileListResponse;
import com.devengine.dto.drive.DriveResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/drive")
public interface DriveController {

    @Operation(summary = "Fetch All The Drives Present in the System")
    @ApiResponse(responseCode = "200", description = "Successfully Fetched the Drives")
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    @GetMapping("/getDrives")
    ResponseEntity<List<DriveResponseDto>> getDrives();


    @Operation(summary = "Fetch All The Files From a Drive/Folder")
    @ApiResponse(responseCode = "200", description = "Successfully Fetched the Files")
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    @PostMapping("/getDriveFiles")
    ResponseEntity<List<DriveFileListResponse>> getDriveFiles(
            @RequestBody DriveFileListRequest request
    );


    @Operation(summary = "View a File")
    @PostMapping("/viewFiles")
    ResponseEntity<Resource> viewFile(@RequestParam String path);


    @Operation(summary = "Download a File")
    @PostMapping("/downloadFiles")
    ResponseEntity<Resource> downloadFile(@RequestParam String path);

}