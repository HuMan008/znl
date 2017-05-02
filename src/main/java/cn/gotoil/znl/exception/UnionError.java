package cn.gotoil.znl.exception;

import cn.gotoil.bill.exception.BillError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.16:47
 */
@Getter
@AllArgsConstructor
@ToString
public enum  UnionError implements BillError {

    puidError(3000,"puid格式不正确，长度32位，只能包含数字和小写字母")    ,

    AppIdError(3001,"请求头中不包含AppId 或者AppId长度限制在0-8位;并且必须已字母或者数字开头，并且不能包含特殊字符")  ,

    OrderAmonutError(3002,"订单金额不能小于1")   ,

    OrderFeeError(3003,"订单数量、单价与订单金额逻辑错误")   ,

    WxCodeNoSupport(3004,"未获取到正确的Wechat Code"),

    ;
    private int code;

    private String message;


}
