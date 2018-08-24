package cn.gotoil.znl.exception;

import cn.gotoil.bill.exception.BillError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.16:40
 */
@Getter
@ToString
@AllArgsConstructor
public enum ZnlError  implements BillError {

    NetConnectTimeOut(0001,"Net Connect Time Out"),

    ParamIllegal(0002,"参数不合法！"),

    AppUnSupportPayeType(0003,"APP不支持此类支付"),

    GetAccountConfigError(0005,"获取配置文件出错"),

    ;


    private int code;

    private String message;
}
