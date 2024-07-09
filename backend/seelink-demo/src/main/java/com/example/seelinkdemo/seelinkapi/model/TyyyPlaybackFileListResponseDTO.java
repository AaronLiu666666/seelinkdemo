package com.example.seelinkdemo.seelinkapi.model;

import lombok.Data;

import java.util.List;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 20:27
 */
@Data
public class TyyyPlaybackFileListResponseDTO {

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalCount;

    private List<PlaybackFileDTO> list;


}

