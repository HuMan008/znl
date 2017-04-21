package cn.gotoil.znl.exception;

import cn.gotoil.bill.exception.BillError;
import cn.gotoil.bill.exception.BillException;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.17:03
 */
public class UnionException extends BillException {


    public UnionException(int tickcode, String message) {
        super(tickcode, message);
    }

    public UnionException(BillError err) {
        super(err);
    }
}
