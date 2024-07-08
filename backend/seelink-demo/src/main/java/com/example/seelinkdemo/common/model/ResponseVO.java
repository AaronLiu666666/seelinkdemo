package com.example.seelinkdemo.common.model;

import com.example.seelinkdemo.common.enums.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;

import java.util.function.Supplier;

/**
 * 通用返回类，包含状态信息，对外的接口，结果使用此VO包装
 *
 * @author ross
 * @date 2022/03/16
 * @email rossmartin@189.cn
 */
@Data
public class ResponseVO<R> {

    private Integer code;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private R content;

    /**
     * 通过返回类型枚举直接生成对应对象，读取枚举默认信息
     *
     * @param responseEnum 返回类型枚举
     * @param <R>          返回体对象类型
     * @return 初步生成的返回对象
     * @see ResponseEnum
     */
    public static <R> ResponseVO<R> responseVO(@NonNull ResponseEnum responseEnum) {
        ResponseVO<R> responseVO = new ResponseVO<>();
        responseVO.setCode(responseEnum.getCode());
        responseVO.setMsg(responseEnum.getDefaultMsg());
        return responseVO;
    }

    /**
     * 通过返回类型枚举直接生成对应对象，读取枚举默认信息
     *
     * @param code 返回类型编码
     * @param customMsg       返回体消息
     * @param <R>          返回体对象类型
     * @return 初步生成的返回对象
     * @see ResponseEnum
     */
    public static <R> ResponseVO<R> responseVO(Integer code, String customMsg,R content) {
        ResponseVO<R> responseVO = new ResponseVO<>();
        responseVO.setCode(code);
        responseVO.setMsg(customMsg);
        responseVO.setContent(content);
        return responseVO;
    }

    /**
     *
     * @param code 返回码
     * @param msg 返回信息
     * @return 初步生成的返回对象
     * @param <R> 返回体对象类型
     */
    public static <R> ResponseVO<R> responseVO(int code, String msg) {
        ResponseVO<R> responseVO = new ResponseVO<>();
        responseVO.setCode(code);
        responseVO.setMsg(msg);
        return responseVO;
    }

    /**
     * 通过布尔类型函数式接口直接生成对应对象，设置默认信息
     * true->success,false->failure
     *
     * @param predicateSupplier 比较类型值
     * @param <R>               返回体对象类型
     * @return 初步生成的返回对象
     * @see ResponseEnum
     */
    public static <R> ResponseVO<R> responseVO(Supplier<Boolean> predicateSupplier) {
        return predicateSupplier.get() ? ResponseVO.responseVO(ResponseEnum.SUCCESS) : ResponseVO.responseVO(ResponseEnum.FAILURE);
    }

    /**
     * 通过布尔类型直接生成对应对象，设置默认信息
     * true->success,false->failure
     *
     * @param bool 布尔类型
     * @param <R>  返回体对象类型
     * @return 初步生成的返回对象
     * @see ResponseEnum
     */
    public static <R> ResponseVO<R> responseVO(boolean bool) {
        return bool ? ResponseVO.responseVO(ResponseEnum.SUCCESS) : ResponseVO.responseVO(ResponseEnum.FAILURE);
    }

    /**
     * 通过返回类型枚举直接生成对应对象，读取枚举默认信息
     *
     * @param responseEnum 返回类型枚举
     * @param <R>          返回体对象类型
     * @return 初步生成的返回对象
     * @see ResponseEnum
     */
    public static <R> ResponseVO<R> responseVO(@NonNull ResponseEnum responseEnum, String customMsg) {
        ResponseVO<R> responseVO = new ResponseVO<>();
        responseVO.setCode(responseEnum.getCode());
        responseVO.setMsg(customMsg);
        return responseVO;
    }

    /**
     * 默认成功方法
     *
     * @param content 成功内容
     * @param <R>     content对象类型
     * @return 结果
     */
    public static <R> ResponseVO<R> success(R content) {
        ResponseVO<R> responseVO = responseVO(ResponseEnum.SUCCESS);
        responseVO.setContent(content);
        return responseVO;
    }


    /**
     * 默认空成功方法
     *
     * @param <R> content对象类型
     * @return 结果
     */
    public static <R> ResponseVO<R> voidSuccess() {
        return responseVO(ResponseEnum.SUCCESS);
    }

    /**
     * 空content成功方法
     *
     * @param content 成功内容
     * @param <R>     content对象类型
     * @return 结果
     */
    @Deprecated
    public static <R> ResponseVO<R> successWithEmpty(R content) {
        ResponseVO<R> responseVO = responseVO(ResponseEnum.SUCCESS);
        responseVO.setContent(null);
        return responseVO;
    }

    /**
     * 默认失败方法
     *
     * @param content 失败内容
     * @param <R>     content对象类型
     * @return 结果
     */
    public static <R> ResponseVO<R> failure(R content) {
        ResponseVO<R> responseVO = responseVO(ResponseEnum.FAILURE);
        responseVO.setContent(content);
        return responseVO;
    }

    /**
     * 默认失败方法，使用自定义失败信息，无错误返回体
     *
     * @param msg 失败信息
     * @param <R> content对象类型
     * @return 结果
     */
    public static <R> ResponseVO<R> failure(String msg) {
        ResponseVO<R> responseVO = responseVO(ResponseEnum.FAILURE);
        responseVO.setMsg(msg);
        return responseVO;
    }

    /**
     * 默认失败方法
     *
     * @param content 失败内容
     * @param <R>     content对象类型
     * @return 结果
     */
    public static <R> ResponseVO<R> failureCustomize(ResponseEnum responseEnum, R content) {
        ResponseVO<R> responseVO = responseVO(responseEnum);
        responseVO.setContent(content);
        return responseVO;
    }

}
