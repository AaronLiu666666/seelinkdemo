package com.example.seelinkdemo.seelinkapi.model;

import lombok.Data;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 20:29
 */
@Data
public class PlaybackFileDTO {

    private String id;

    private String name;

    private String iconUrl;

    private Integer size;

    private String createDate;

    private String lastOpTime;

    private String fileType;


}

