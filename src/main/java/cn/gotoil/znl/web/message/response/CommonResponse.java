package cn.gotoil.znl.web.message.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wh on 2017/4/20.
 */
@Getter
@Setter
@Builder
public class CommonResponse {

    public static  final  int NormalStatusCode = 200;

    /***
     * 是否成功
     * **/
    private  boolean  success;
    /***
     * 说明信息
     * **/
    private  String  msg;

    /***
     * 状态标示码
     * **/
    private  int  code;
    /***
     * 真正的数据部分
     * **/
    private  Object  datas;


    public CommonResponse() {
        this.code = NormalStatusCode;
    }

    public CommonResponse(boolean success, String msg, int code, Object datas) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        this.datas = datas;
    }
}
