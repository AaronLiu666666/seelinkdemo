package com.example.seelinkdemo.seelinkapi.controller;

import com.example.seelinkdemo.common.model.ResponseVO;
import com.example.seelinkdemo.seelinkapi.model.TYYYRegionDTO;
import com.example.seelinkdemo.seelinkapi.model.TYYYRegionRequestDTO;
import com.example.seelinkdemo.seelinkapi.model.TyyyAccessTokenVO;
import com.example.seelinkdemo.seelinkapi.service.SeeLinkApiService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 17:09
 */
@RestController
@Slf4j
@RequestMapping("/seeLink")
public class SeeLinkController {

    @Resource
    private SeeLinkApiService seeLinkApiService;

    @GetMapping("/getAccessToken")
    public ResponseVO<TyyyAccessTokenVO> getAccessToken(){
        String accessToken = seeLinkApiService.getAccessToken();
        TyyyAccessTokenVO tyyyAccessTokenVO = new TyyyAccessTokenVO();
        tyyyAccessTokenVO.setToken(accessToken);
        return ResponseVO.success(tyyyAccessTokenVO);
    }

    @GetMapping("/getReginTree")
    public ResponseVO<TYYYRegionDTO> getReginWithGroupList(){
        TYYYRegionDTO tyyyRegionDTO = seeLinkApiService.getReginWithGroupList(new TYYYRegionRequestDTO());
        return ResponseVO.success(tyyyRegionDTO);
    }



}

