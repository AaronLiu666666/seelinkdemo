package com.example.seelinkdemo.seelinkapi.controller;

import com.example.seelinkdemo.common.constants.SeeLinkConstant;
import com.example.seelinkdemo.common.model.ResponseVO;
import com.example.seelinkdemo.seelinkapi.model.*;
import com.example.seelinkdemo.seelinkapi.service.SeeLinkApiService;
import com.example.seelinkdemo.seelinkapi.service.SeeLinkVideoApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 17:09
 */
@RestController
@Slf4j
@RequestMapping("/seeLink")
@Tag(name = "天翼视联接口")
public class SeeLinkController {

    @Resource
    private SeeLinkApiService seeLinkApiService;

    @Resource
    private SeeLinkVideoApiService seeLinkVideoApiService;

    @Operation(summary = "获取访问令牌", description = "无感知获取访问令牌")
    @GetMapping("/getAccessToken")
    public ResponseVO<TyyyAccessTokenVO> getAccessToken() {
        String accessToken = seeLinkApiService.getAccessToken();
        TyyyAccessTokenVO tyyyAccessTokenVO = new TyyyAccessTokenVO();
        tyyyAccessTokenVO.setToken(accessToken);
        return ResponseVO.success(tyyyAccessTokenVO);
    }

    @Operation(summary = "根据区域id获取区域下级信息")
    @GetMapping("/getRegion")
    public ResponseVO<List<TYYYRegionDTO>> getRegion(String regionId) {
        TYYYRegionRequestDTO tyyyRegionRequestDTO = new TYYYRegionRequestDTO();
        tyyyRegionRequestDTO.setRegionId(regionId);
        tyyyRegionRequestDTO.setAccessToken(seeLinkApiService.getAccessToken());
        tyyyRegionRequestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        List<TYYYRegionDTO> result = seeLinkApiService.getReginWithGroupList(tyyyRegionRequestDTO);
        return ResponseVO.success(result);
    }

    @Operation(summary = "获取区域+设备树")
    @GetMapping("/getRegionTree")
    public ResponseVO<TYYYRegionDTO> getRegionTree() {
        TYYYRegionDTO regionDTO = seeLinkApiService.getRegionTree();
        return ResponseVO.success(regionDTO);
    }

    @Operation(summary = "分页获取设备")
    @GetMapping("/getDevicePage")
    public ResponseVO<TyyyGetDeviceListResponseDTO> getDevicePage(GetDevicePageRequestDTO requestDTO) {
        TyyyGetDeviceListRequestDTO tyyyGetDeviceListRequestDTO = new TyyyGetDeviceListRequestDTO();
        tyyyGetDeviceListRequestDTO.setPageNo(requestDTO.getPage());
        tyyyGetDeviceListRequestDTO.setPageSize(requestDTO.getPageSize());
        tyyyGetDeviceListRequestDTO.setRegionId(requestDTO.getRegionId());
        TyyyGetDeviceListResponseDTO tyyyGetDeviceListResponseDTO = seeLinkApiService.getDeviceList(tyyyGetDeviceListRequestDTO);
        return ResponseVO.success(tyyyGetDeviceListResponseDTO);
    }


    @Operation(summary = "获取直播流")
    @GetMapping("getStreamUrl")
    public ResponseVO<StreamUrlDTO> getStreamUrl(String deviceCode) {
        TyyyGetDeviceMediaUrlHlsRequestDTO tyyyGetDeviceMediaUrlHlsRequestDTO = new TyyyGetDeviceMediaUrlHlsRequestDTO();
        tyyyGetDeviceMediaUrlHlsRequestDTO.setDeviceCode(deviceCode);
        StreamUrlDTO streamUrl = seeLinkVideoApiService.getStreamUrl(tyyyGetDeviceMediaUrlHlsRequestDTO);
        return ResponseVO.success(streamUrl);
    }


    @Operation(summary = "获取回放文件列表")
    @GetMapping("getPlaybackFileList")
    public ResponseVO<?> getPlaybackFileList(TYYYGetPlaybackFileListRequestDTO requestDTO) {
        TyyyPlaybackFileListResponseDTO playbackFileList = seeLinkVideoApiService.getPlaybackFileList(requestDTO);
        return ResponseVO.success(playbackFileList);
    }

    @Operation(summary = "获取回放视频流")
    @GetMapping("getPlaybackStreamUrl")
    public ResponseVO<?> getPlaybackStreamUrl(TyyyPlaybackStreamUrlRequestDTO requestDTO) {
        TyyyPlaybackStreamUrlResponseDTO responseDTO = seeLinkVideoApiService.getPlaybackStreamUrl(requestDTO);
        return ResponseVO.success(responseDTO);
    }

    @Operation(summary = "获取回访文件下载链接")
    @GetMapping("getFileUrlById")
    public ResponseVO<?> getFileUrlById(TyyyGetFileUrlByIdRequestDTO requestDTO) {
        TyyyGetFileUrlByIdResponseDTO responseDTO = seeLinkVideoApiService.getFileUrlById(requestDTO);
        return ResponseVO.success(responseDTO);
    }

}

