package com.znl.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/17.11:05
 */
@Getter
@AllArgsConstructor
public enum ValidatorEnum {
    RequestArgVailiError(9001,"参数校验失败");

    private int code;
    private String message;





}
