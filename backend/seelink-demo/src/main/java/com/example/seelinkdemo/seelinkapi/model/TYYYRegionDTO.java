package com.example.seelinkdemo.seelinkapi.model;

import lombok.Data;

import java.util.List;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 17:28
 */
@Data
public class TYYYRegionDTO {
    private int id;
    private int havDevice;
    private String name;
    private int level;
    private String regionCode;
    private String regionGBId;
    private int hasChildren;

    List<TYYYRegionDTO> children;

    List<TyyyGetDeviceDTO> deviceList;

}

