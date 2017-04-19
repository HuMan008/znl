/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin com.iusworks.dolphin.web.message.DolphinResponse
 *
 * Suyj <yizhishita@126.com>,  November 2016
 *
 * LastModified: 11/3/16 10:07 AM
 *
 */

package cn.gotoil.znl.web.message;


public class MicroServerResponse {


    private int status;

    private String message;

    private Object data;

    public MicroServerResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public MicroServerResponse(Object data) {
        this.data = data;
        this.status = 0;
    }

    public MicroServerResponse(MicroServerLogicError logicError) {
        this.status = logicError.getCode();
        this.message = logicError.getMessage();
    }

    public MicroServerResponse(MicroServerLogicError logicError, Object data) {
        this.status = logicError.getCode();
        this.message = logicError.getMessage();
        this.data = data;
    }

    public MicroServerResponse() {
    }

    public static void unFindData(MicroServerResponse response) {
        response.setStatus(99);
        response.setMessage("未查询到数据");
    }

    public static MicroServerResponse defaultResponse() {
        MicroServerResponse response = new MicroServerResponse();
        response.setStatus(0);
        response.setMessage("success");
        return response;
    }

    @Override
    public String toString() {
        return "MicroServerResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MicroServerResponse)) return false;

        MicroServerResponse that = (MicroServerResponse) o;

        if (status != that.status) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;

    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
