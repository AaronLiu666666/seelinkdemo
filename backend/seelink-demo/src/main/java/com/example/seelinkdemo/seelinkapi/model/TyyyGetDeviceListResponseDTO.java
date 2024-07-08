package com.example.seelinkdemo.seelinkapi.model;

import lombok.Data;

import java.util.List;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 18:52
 */
@Data
public class TyyyGetDeviceListResponseDTO {

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalCount;

    private List<TyyyGetDeviceDTO> list;

}

