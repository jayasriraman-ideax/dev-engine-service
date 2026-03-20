package com.devengine.dto.drive;

import lombok.Data;

@Data
public class DriveFileListResponse {

    private String name;
    private String path;
    private boolean directory;
    private long size;

}