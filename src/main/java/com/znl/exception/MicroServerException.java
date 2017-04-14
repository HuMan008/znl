package com.znl.exception;


public class MicroServerException extends RuntimeException {

    private int tickcode;

    public MicroServerException(int tickcode, String message) {
        super(message);
        this.tickcode = tickcode;
    }

    public int getTickcode() {
        return tickcode;
    }

}
