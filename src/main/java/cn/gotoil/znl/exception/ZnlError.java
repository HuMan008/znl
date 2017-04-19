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

    NetConnectTimeOut(0001,"Net Connect Time Out")
    ;


    private int code;

    private String message;
}
