package cn.gotoil.znl.exception;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.11:01
 */
public class AuthenticationException extends WebException {
    public AuthenticationException(ValidatorEnum exenum) {
        super(exenum);
    }
}
