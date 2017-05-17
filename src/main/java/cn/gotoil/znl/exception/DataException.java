package cn.gotoil.znl.exception;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/2/24.10:54
 */
public class DataException extends RuntimeException {

    private boolean success;
    private String msg;

    public DataException(boolean success, String msg) {
        super(msg);
        this.msg = msg;
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}