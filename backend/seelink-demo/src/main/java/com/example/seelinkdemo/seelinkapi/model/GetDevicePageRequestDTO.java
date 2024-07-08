package com.example.seelinkdemo.seelinkapi.model;

import lombok.Data;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 18:58
 */
@Data
public class GetDevicePageRequestDTO {

    private String regionId;

    private Integer page=1;

    private Integer pageSize=10;

}

