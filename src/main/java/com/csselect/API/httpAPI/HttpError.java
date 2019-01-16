package com.csselect.API.httpAPI;

public class HttpError extends Exception{
    private int errorCode;
    public HttpError(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
