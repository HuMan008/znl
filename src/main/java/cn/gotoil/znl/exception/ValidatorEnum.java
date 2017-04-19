package cn.gotoil.znl.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/17.11:05
 */
@Getter
@AllArgsConstructor
public enum ValidatorEnum {
    RequestArgVailiError(9001,"参数校验失败"),

/*    RequestNoAppId(9002,"Request head  con't find AppId") ,
    RequestNoSign(9003,"Request head  con't find Sign") ,
    RequestNoUserAgent(9004,"Request head  con't find User-Agent") ,*/
    Entegrity(9005, "Request verify failed"),
      ;
    private int code;
    private String message;





}
