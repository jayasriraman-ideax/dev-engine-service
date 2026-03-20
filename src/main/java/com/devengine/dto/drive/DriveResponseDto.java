package com.devengine.dto.drive;

import lombok.Data;

@Data
public class DriveResponseDto {
                
    private String driveName;
    private double totalSpace;
    private double freeSpace;
    private double usedSpace;

}